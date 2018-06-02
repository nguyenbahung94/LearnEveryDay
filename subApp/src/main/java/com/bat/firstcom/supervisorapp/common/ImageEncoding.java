package com.bat.firstcom.supervisorapp.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tung phan on 8/11/17.
 */

public final class ImageEncoding {

    private static final int REQUIRE_WIDTH = 500;
    private static final int BYTE_BUFFER = 1024;
    private static final int START_OFFSET = 0;

    //encode bitmap to String base 64
    public static String encode(Bitmap bm) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            String encImage = Base64.encodeToString(b, Base64.DEFAULT);
            return encImage;
        } catch (Exception ex) {
            ex.printStackTrace();
            return Strings.EMPTY;
        }
    }

    public static Bitmap decodeBitmapFrom(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            int n;
            byte[] buffer = new byte[BYTE_BUFFER];
            while ((n = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, START_OFFSET, n);
            }
            return decodedBitmap(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //resize image before encoded it.
    private static Bitmap decodedBitmap(byte[] data) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, REQUIRE_WIDTH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth) {
        // Raw width of image
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (width > reqWidth) {
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps
            // width larger than the requested width.
            while ((halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize * 2;
    }

}
