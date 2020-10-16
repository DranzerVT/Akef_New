package com.android.akef.Interfaces;

/**
 *call this interface if you want to return a result after completing some database operation
 * parameter is of Object type so it can be casted to anything as needed
 */
public  interface DatabaseFetchListener {

    default <T> void onLoadingFinished(T o) {};

    default <T> void onMultipleObjLoadingFinished(T... o) {
    }

}
