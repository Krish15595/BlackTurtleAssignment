package com.example.blackturtleassignment.services

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import java.io.File


class DownloadService : IntentService("DownloadSongService") {
    override fun onHandleIntent(@Nullable intent: Intent?) {
        val downloadPath =
            intent!!.getStringExtra(DOWNLOAD_PATH)
        val destinationPath =
            intent.getStringExtra(DESTINATION_PATH)
        startDownload(downloadPath, destinationPath)
    }

    private fun startDownload(
        downloadPath: String,
        destinationPath: String
    ) {
        val uri: Uri = Uri.parse(downloadPath) // Path where you want to download file.
//        Log.i(TAG, "startDownload: "+downloadPath)
        var img_directory: File? = null

        img_directory = File(Environment.getExternalStorageDirectory().toString() + "/Download")
        if(!img_directory.exists())
        {
            img_directory.mkdir()
            val request = DownloadManager.Request(uri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI) // Tell on which network you want to download file.
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // This will show notification on top when downloading the file.
            request.setTitle("Downloading a file") // Title for notification.
            request.setVisibleInDownloadsUi(true)
            request.setDestinationInExternalPublicDir(
                Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download",
                uri.getLastPathSegment()
            ) // Storage directory path
            (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request) // This will start downloading
        }

    }

    companion object {
        private const val DOWNLOAD_PATH =
            "com.example.blackturtleassignment_downloadpath"
        private const val DESTINATION_PATH =
            "com.example.blackturtleassignment_destinationpath"

        fun getDownloadService(
            @NonNull callingClassContext: Context?,
            @NonNull downloadPath: String?,
            @NonNull destinationPath: String?
        ): Intent {
            return Intent(callingClassContext, DownloadService::class.java)
                .putExtra(DOWNLOAD_PATH, downloadPath)
                .putExtra(DESTINATION_PATH, destinationPath)
        }
    }
}
