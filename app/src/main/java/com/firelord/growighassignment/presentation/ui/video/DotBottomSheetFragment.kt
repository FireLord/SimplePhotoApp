package com.firelord.growighassignment.presentation.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firelord.growighassignment.databinding.DotBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DotBottomSheetFragment: BottomSheetDialogFragment() {
    private lateinit var dotBottomSheetBinding : DotBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dotBottomSheetBinding = DotBottomSheetBinding.inflate(inflater)
        return dotBottomSheetBinding.root
    }
}