/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
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
package frame.base.com.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

import frame.base.com.R;
import frame.base.com.publicutils.AlertManager;
import frame.base.com.publicviews.CirclePageIndicator;
import frame.base.com.publicviews.HackyViewPager;
import frame.base.com.publicviews.PageIndicator;
import frame.base.com.publicviews.PhotoView;
import frame.base.com.publicviews.PhotoViewAttacher;
import frame.base.com.publicviews.RoundTransformation;

/**
 * 
 * @author LiuGuoyan
 guide:
 Intent in=new Intent(getActivity(), ImagePagerActivity.class);
 in.putExtra(ImagePagerActivity.IMAGES | IMAGE_POSITION, String[] imageUrls);
 startActivity(in);
 *
 */
public class ImagePagerActivity extends StepActivity {

	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String SHOW_POSITION = "show_position";

	/** 要显示图片的 url  */
	public static final String IMAGES = "images";
	
	/** 默认的图片显示位置  */
	public static final String IMAGE_POSITION = "image_index";
	
	/** 是否包含保存和分享按钮,默认的是不包含 */
	public static final String INTENT_EXTRA_SAVE_SHARE= "intent_extra_save_share";
	
	/** 是否包含保存和分享按钮,默认的是不包含 */
	private boolean intent_extra_save_share = false; 
	
	HackyViewPager pager;
	PageIndicator mIndicator;
	private int showMark=0;

	public  int IMG_CIRCULAR_BEAD = 20;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_pager);
		

		Bundle bundle = getIntent().getExtras();
		String[] imageUrls = bundle.getStringArray(IMAGES);
		int pagerPosition = bundle.getInt(IMAGE_POSITION, 0);
		intent_extra_save_share = getIntent().getBooleanExtra(INTENT_EXTRA_SAVE_SHARE, false) ;
		showMark=getIntent().getIntExtra(SHOW_POSITION,0);
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		

		pager = (HackyViewPager) findViewById(R.id.pager);
		pager.setAdapter(new ImagePagerAdapter(imageUrls,this));
		if (showMark != 0&&imageUrls.length>showMark) {
			pager.setCurrentItem(showMark);
			showMark=0;
		} else {
			pager.setCurrentItem(pagerPosition);
		}

		mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
	    mIndicator.setViewPager(pager);
	    
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, pager.getCurrentItem());
	}
	
	/** 保存图片 */
	public static final int MSG_WHAT_SAVE= 0x0;
	/** 分享图片  */
	public static final int MSG_WHAT_SHARE= 0x1;
	/** 图片的url */
	public static final String MSG_BUNDLE_KEY_IMG_URL= "msg_bundle_key_img_url";
	
	private class ImagePagerAdapter extends PagerAdapter {

		private String[] images;
		private LayoutInflater inflater;
		private Context mContext;

		ImagePagerAdapter(String[] images, Context context) {
			this.images = images;
			this.mContext=context;
			inflater = getLayoutInflater();
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}
		
		@Override
		public void finishUpdate(View container) {
		}
		
		@Override
		public int getCount() {
			return images.length;
		}
		
		@Override
		public Object instantiateItem(ViewGroup view, final int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			imageLayout.findViewById(R.id.loading_number).setVisibility(View.INVISIBLE) ;
			
			PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);
			imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
				@Override
				public void onViewTap(View view, float x, float y) {
					ImagePagerActivity.this.finish();
				}
			});
			
			/** 如果要显示操作按钮,才进行此操作 */
			if (intent_extra_save_share) {
				RelativeLayout layout = (RelativeLayout) imageLayout.findViewById(R.id.layout_oprations) ;
				layout.setVisibility(View.VISIBLE) ;
				TextView txt_save = (TextView) imageLayout.findViewById(R.id.txt_save) ;
				TextView txt_share = (TextView) imageLayout.findViewById(R.id.txt_share) ;
				txt_save.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Message msg = Message.obtain() ;
						msg.what = MSG_WHAT_SAVE ; 
						Bundle data = new Bundle() ;
						data.putString(MSG_BUNDLE_KEY_IMG_URL, images[position]) ;
						msg.setData(data) ; 
						getDefaultHandler().sendMessage(msg) ; 
					}
				}) ; 
				txt_share.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Message msg = Message.obtain() ;
						msg.what = MSG_WHAT_SHARE ; 
						Bundle data = new Bundle() ;
						data.putString(MSG_BUNDLE_KEY_IMG_URL, images[position]) ;
						msg.setData(data) ; 
						getDefaultHandler().sendMessage(msg) ;
					}
				}) ; 
			}
			
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

			getBitmap(imageView,images[position],IMG_CIRCULAR_BEAD);

			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}
	}


	public void getBitmap(ImageView imageView, String url, int circularBead) {
		try {
			Glide.with(getActivity())
					.load(url)
					//                        .override(width,height)//这里的单位是px,可以设置显示大小

					/**
					 * DiskCacheStrategy 的枚举意义：

					 DiskCacheStrategy.NONE 什么都不缓存
					 DiskCacheStrategy.SOURCE 只缓存全尺寸图
					 DiskCacheStrategy.RESULT 只缓存最终的加载图
					 DiskCacheStrategy.ALL 缓存所有版本图（默认行为）
					 **/
					//                        .skipMemoryCache(true)
					//                        .diskCacheStrategy( DiskCacheStrategy.NONE )


					//                        .priority( Priority.LOW )//显示优先级
					.transform(new RoundTransformation(getActivity() , circularBead))
					.crossFade()//或者使用 dontAnimate() 关闭动画
					.placeholder(R.drawable.ic_launcher)//图片加载出来前，显示的图片
					.error(R.drawable.ic_launcher)//图片加载失败后，显示的图片
					.into(imageView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void createContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void free() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onHandleMessage(Message msg) {
		String url = msg.getData().getString(MSG_BUNDLE_KEY_IMG_URL) ;
		if (msg.what == MSG_WHAT_SAVE) {
			save2Album(url) ;
		} else if (msg.what == MSG_WHAT_SHARE) {
			String share_img="";
			if (url != null && url.length() > 0) {
				share_img = url.replaceAll("images", "img");
			}
//			ShareActivityHelper.share(getActivity(), getString(R.string.shared_pic_title), share_img, share_img, String.format(getResources().getString(R.string.shared_pic_descrpition), share_img));
		}
	}
	
	public void save2Album(String url){
		
		HttpUtils utils = new HttpUtils() ;
		
		final String subForder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator ;
		final String target = subForder + "dmzj-"+ System.currentTimeMillis()+".jpg" ;
		utils.download(url, target, new RequestCallBack<File>() {
			@Override
			public void onStart() {
				AlertManager.getInstance().showHint(getActivity(), AlertManager.HintType.HT_FAILED, getString(R.string.shared_2_save_album_start_save)) ;
			}
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				String toast = String.format(getString(R.string.shared_2_save_album_success), arg0.result.toString()) ;
				AlertManager.getInstance().showHint(getActivity(), AlertManager.HintType.HT_FAILED, toast) ;
				
				//发送广播
				Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.fromFile(new File(subForder));
				intent.setData(uri);
				getActivity().sendBroadcast(intent);	
			}
			@Override
			public void onFailure(HttpException arg0, String arg1) {
			}
		}) ; 
		
	}
	
	
}