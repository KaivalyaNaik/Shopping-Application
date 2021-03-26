package com.example.shoppingapplication.activities.homeScreens

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import com.example.shoppingapplication.R

class VideoView : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.video_view, container, false)

        val uri=Uri.parse("https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_480_1_5MG.mp4")

        val videoView =view.findViewById<VideoView>(R.id.videoView2)

        videoView.setVideoURI(uri)

        val player:MediaController= MediaController(view.context)
        videoView.setMediaController(player)
        player.setAnchorView(videoView)


        return view
    }


}