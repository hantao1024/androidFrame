/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package frame.base.com.publicviews;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;


public class PhotoView extends LoadImageView implements IPhotoView {
	
	private PhotoViewAttacher mAttacher;
	
	private ScaleType mPendingScaleType;
	
	public PhotoView(Context context) {
		this(context, null);
	}
	
	public PhotoView(Context context, AttributeSet attr) {
		this(context, attr, 0);
	}
	
	public PhotoView(Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		super.setScaleType(ScaleType.MATRIX);
		setmAttacher(new PhotoViewAttacher(this));
		if (null != mPendingScaleType) {
			setScaleType(mPendingScaleType);
			mPendingScaleType = null;
		}
	}
	
	public void setOnTouchedListener(PhotoViewAttacher.OnTouchedListener mOnTouchedListener){
		if (mAttacher!=null) {
			mAttacher.setOnTouchedListener(mOnTouchedListener);
		}
	}
	
	@Override
	public boolean canZoom() {
		return getmAttacher().canZoom();
	}
	
	public void setSuperScaleType(ScaleType scaleType){
		super.setScaleType(scaleType);
	}
	
	@Override
	public RectF getDisplayRect() {
		return getmAttacher().getDisplayRect();
	}

	@Override
	public float getMinScale() {
		return getmAttacher().getMinScale();
	}

	@Override
	public float getMidScale() {
		return getmAttacher().getMidScale();
	}

	@Override
	public float getMaxScale() {
		return getmAttacher().getMaxScale();
	}

	@Override
	public float getScale() {
		return getmAttacher().getScale();
	}

	@Override
	public ScaleType getScaleType() {
		return getmAttacher().getScaleType();
	}

    @Override
    public void setAllowParentInterceptOnEdge(boolean allow) {
        getmAttacher().setAllowParentInterceptOnEdge(allow);
    }

    @Override
	public void setMinScale(float minScale) {
		getmAttacher().setMinScale(minScale);
	}

	@Override
	public void setMidScale(float midScale) {
		getmAttacher().setMidScale(midScale);
	}

	@Override
	public void setMaxScale(float maxScale) {
		getmAttacher().setMaxScale(maxScale);
	}

	@Override
	// setImageBitmap calls through to this method
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		if (null != getmAttacher()) {
			getmAttacher().update();
		}
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		if (null != getmAttacher()) {
			getmAttacher().update();
		}
	}

	@Override
	public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		if (null != getmAttacher()) {
			getmAttacher().update();
		}
	}

	@Override
	public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener) {
		getmAttacher().setOnMatrixChangeListener(listener);
	}

	@Override
	public void setOnLongClickListener(OnLongClickListener l) {
		getmAttacher().setOnLongClickListener(l);
	}

	@Override
	public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener) {
		getmAttacher().setOnPhotoTapListener(listener);
	}

	@Override
	public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener) {
		getmAttacher().setOnViewTapListener(listener);
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if (null != getmAttacher()) {
			getmAttacher().setScaleType(scaleType);
		} else {
			mPendingScaleType = scaleType;
		}
	}

	@Override
	public void setZoomable(boolean zoomable) {
		getmAttacher().setZoomable(zoomable);
	}

	@Override
	public void zoomTo(float scale, float focalX, float focalY) {
		getmAttacher().zoomTo(scale, focalX, focalY);
	}

	@Override
	protected void onDetachedFromWindow() {
		getmAttacher().cleanup();
		super.onDetachedFromWindow();
	}

	public PhotoViewAttacher getmAttacher() {
		return mAttacher;
	}

	public void setmAttacher(PhotoViewAttacher mAttacher) {
		this.mAttacher = mAttacher;
	}

}