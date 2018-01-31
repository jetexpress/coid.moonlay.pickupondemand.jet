package coid.moonlay.pickupondemand.jet.fragment;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.custom.TouchImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagePreviewDialogFragment extends DialogFragment
{
    private final static String IMAGE_BITMAP_ARGS_PARAM = "ImageBitmapParam";
    private Bitmap mImageBitmap;
    private TouchImageView img_bitmap_preview;


    public ImagePreviewDialogFragment()
    {
        // Required empty public constructor
    }

    public static ImagePreviewDialogFragment getInstance(Bitmap imageBitmap)
    {
        ImagePreviewDialogFragment fragment = new ImagePreviewDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(IMAGE_BITMAP_ARGS_PARAM, imageBitmap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog);
        if (getArguments() != null)
            mImageBitmap = getArguments().getParcelable(IMAGE_BITMAP_ARGS_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_preview_dialog, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img_bitmap_preview = (TouchImageView) view.findViewById(R.id.img_bitmap_preview);
        img_bitmap_preview.setImageBitmap(mImageBitmap);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d!=null){
            d.getWindow().setGravity(Gravity.CENTER);
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.setCancelable(true);
            d.setCanceledOnTouchOutside(true);
        }
    }
}
