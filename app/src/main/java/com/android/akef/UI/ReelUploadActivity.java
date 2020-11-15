package com.android.akef.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;
import com.android.akef.Adapters.VideoListAdapter;
import com.android.akef.Database.Repository;
import com.android.akef.Interfaces.VideoClickedListener;
import com.android.akef.R;
import com.android.akef.Tables.User;
import com.android.akef.Utils.Reel;
import com.android.akef.Utils.Variables;
import com.android.akef.Utils.VideoModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.errorprone.annotations.Var;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReelUploadActivity extends AppCompatActivity {

    FloatingActionButton fabVideoUpload;
    RecyclerView videoListView;
    VideoView videoPreview;
    VideoModel selectedVideo = null;
    String sourcePath;
    File destFile;
    FirebaseStorage storage;
    File myDir;
    Repository repository;
    ProgressDialog progressDialog;
    String videoTitle;
    String videoDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reel_upload);
        initLayout();
        loadVideos();
        storage = FirebaseStorage.getInstance();
        myDir = new File(this.getFilesDir() + "/temp");
        if (!myDir.exists()) {
            boolean success = myDir.mkdirs();
            Log.e("ReelUploadActivity", "Directory Creation: " + success);
        }
        repository = Repository.getInstance(getApplication());
        fabVideoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedVideo == null || selectedVideo.getUri() == null) {
                    return;
                }
                sourcePath = null;

               showVideoDetailsDialog();
            }
        });

    }

    public void showVideoDetailsDialog(){

        Dialog dialog = new Dialog(ReelUploadActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.video_details_dialog);
        dialog.setCancelable(true);
        dialog.show();

        EditText edtTitle = dialog.findViewById(R.id.title_edt);
        EditText edtDesc = dialog.findViewById(R.id.desc_edt);
        TextView txtUpload = dialog.findViewById(R.id.upload_text);

        txtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoTitle = edtTitle.getText().toString();
                if(videoTitle.equals("")){
                    Variables.setWarningToast(ReelUploadActivity.this,"Please Enter a Title");
                    return;
                }
                videoDesc = edtDesc.getText().toString();
                if(videoDesc.equals("")){
                    Variables.setWarningToast(ReelUploadActivity.this,"Please Enter Description");
                    return;
                }

                dialog.dismiss();
                showProgress("Uploading in progress..");
                try {
                    sourcePath = getFilePathFromUri(ReelUploadActivity.this, selectedVideo.getUri()).getPath();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                long sizeInKB = selectedVideo.getSize() / 1024;
                long sizeInMB = sizeInKB / 1024;
                Log.e("ReelUploadActivity", "onClick: video size " + sizeInMB);
                if (sizeInMB < 10) {
                    File file = new File(sourcePath);
                    uploadVideo(file);
                } else {
                    compressVideo();
                }

            }
        });

    }

    public void compressVideo() {

        String tempfile = "video";
        destFile = new File(myDir, tempfile);
        Log.e("ReelUploadActivity", "source path: " + sourcePath);

        VideoCompressor.start(sourcePath, destFile.getPath(), new CompressionListener() {
            @Override
            public void onStart() {
                // Compression start
            }

            @Override
            public void onSuccess() {
                Log.e("ReelUploadActivity", "Before compression size: " + checkFileSize(sourcePath) + " MB");
                Log.e("ReelUploadActivity", "After compression size: " + checkFileSize(destFile.getPath()) + " MB");
                uploadVideo(destFile);
            }

            @Override
            public void onFailure(String failureMessage) {
                Log.e("ReelUploadActivity", "onFailure: " + failureMessage);
                dismissProgress();
            }

            @Override
            public void onProgress(float v) {
                Log.e("ReelUploadActivity", "onProgress: " + v);

            }

            @Override
            public void onCancelled() {
                dismissProgress();
            }
        }, VideoQuality.MEDIUM, false, false);

    }

    public void uploadVideo(File destFile) {

        User currentUser = repository.getLoggedInUser();
        long userID = currentUser.getUserID();
        Uri file = Uri.fromFile(destFile);
        StorageReference storageRef = storage.getReference();
        String timestamp = Variables.getDateTimeStamp();
        StorageReference videosRef = storageRef.child("reels/user-" + userID + "/" + timestamp);
        UploadTask uploadTask = videosRef.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("ReelUploadActivity", "onUploadFailure: " + exception.getMessage());
                exception.printStackTrace();
                dismissProgress();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isComplete() && task.isSuccessful()) {
                    videosRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            Log.e("ReelUploadActivity", "downloadUrl: " + task.getResult());
                            uploadToFireStore(task.getResult().toString(), userID, timestamp);
                        }
                    });
                } else {
                    dismissProgress();
                }
            }
        });

    }

    public void uploadToFireStore(String downloadUrl, long userID, String timestamp) {

        // Access a Cloud Firestore instance from your Activity
        String docID = userID + "-" + timestamp;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Reel reel = new Reel(userID, downloadUrl, null,videoTitle,videoDesc);
        db.collection(Variables.COLLECTION_REEL).document(docID).set(reel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dismissProgress();
                        Variables.setSuccessToast(ReelUploadActivity.this, "Upload Success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dismissProgress();
                        Variables.setWarningToast(ReelUploadActivity.this, "Failed to Upload Video");
                        e.printStackTrace();
                    }
                });
    }


    public void loadVideos() {
        List<VideoModel> videoList = fetchVideoList();
        Log.e("ReelUploadActivity", "onCreate: videos : " + videoList.size());
        if (videoList.size() > 0) {
            VideoListAdapter videoListAdapter = new VideoListAdapter(videoList, this, new VideoClickedListener() {
                @Override
                public void onVideoClicked(VideoModel videoModel) {
                    videoPreview.setVideoURI(videoModel.getUri());
                    selectedVideo = videoModel;
                    videoPreview.start();
                    fabVideoUpload.setVisibility(View.VISIBLE);
                }
            });
            videoListView.setLayoutManager(new GridLayoutManager(this, 3));
            videoListView.setAdapter(videoListAdapter);
        }
    }

    public void initLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Upload Reel");
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        fabVideoUpload = findViewById(R.id.fab_upload);
        videoListView = findViewById(R.id.videogrid);
        videoPreview = findViewById(R.id.videopreview);

    }

    public List<VideoModel> fetchVideoList() {

        List<VideoModel> videoList = new ArrayList<VideoModel>();

        String[] projection = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE
        };
        String selection = MediaStore.Video.Media.DURATION +
                " <= ?";
        String[] selectionArgs = new String[]{
                String.valueOf(TimeUnit.MILLISECONDS.convert(Variables.MAX_VIDEO_DURATION, TimeUnit.MINUTES))
        };
        String sortOrder = MediaStore.Video.Media.DATE_MODIFIED + " DESC";

        try (Cursor cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        )) {
            // Cache column indices.
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            int nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                long id = cursor.getLong(idColumn);
                String name = cursor.getString(nameColumn);
                int duration = cursor.getInt(durationColumn);
                int size = cursor.getInt(sizeColumn);

                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videoList.add(new VideoModel(contentUri, name, duration, size));
            }
        }
        return videoList;
    }

    public void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(ReelUploadActivity.this, R.style.AppTheme_Dark_Dialog_ProgressTransparent);
        }
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static Uri getFilePathFromUri(Context context, Uri uri) throws IOException {
        String fileName = getFileName(context, uri);
        File file = new File(context.getExternalCacheDir(), fileName);
        file.createNewFile();
        try (OutputStream outputStream = new FileOutputStream(file);
             InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                FileUtils.copy(inputStream, outputStream); //Simply reads input to output stream
            } else {
                byte[] buffer = new byte[1024];
                int bytesRead;
                //read from is to buffer
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
            }
            outputStream.flush();
        }
        return Uri.fromFile(file);
    }

    public static String getFileName(Context context, Uri uri) {
        String fileName = getFileNameFromCursor(context, uri);
        if (fileName == null) {
            String fileExtension = getFileExtension(context, uri);
            fileName = "temp_file" + (fileExtension != null ? "." + fileExtension : "");
        } else if (!fileName.contains(".")) {
            String fileExtension = getFileExtension(context, uri);
            fileName = fileName + "." + fileExtension;
        }
        return fileName;
    }

    public static String getFileExtension(Context context, Uri uri) {
        String fileType = context.getContentResolver().getType(uri);
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType);
    }

    public static String getFileNameFromCursor(Context context, Uri uri) {
        Cursor fileCursor = context.getContentResolver().query(uri, new String[]{OpenableColumns.DISPLAY_NAME}, null, null, null);
        String fileName = null;
        if (fileCursor != null && fileCursor.moveToFirst()) {
            int cIndex = fileCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (cIndex != -1) {
                fileName = fileCursor.getString(cIndex);
            }
        }
        return fileName;
    }

    public long checkFileSize(String path) {
        File file = new File(path);

        // Get length of file in bytes
        long fileSizeInBytes = file.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
        //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)

        return fileSizeInKB / 1024;
    }

    public long checkFileSize(Uri uri) {
        File file = new File(uri.getPath());

        // Get length of file in bytes
        long fileSizeInBytes = file.length();
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
        //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)

        return fileSizeInKB / 1024;
    }
}