package frame.base.com.base;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import frame.base.com.R;
import frame.base.com.publicviews.RoundTransformation;

/**
 * 基Adapter
 *
 * @param <Da>
 * @author hantao
 */
public abstract class KDBaseAdapter<Da> extends BaseAdapter {



    private Context mContext;

    public  int IMG_CIRCULAR_BEAD = 20;
    /**
     * 列表中显示的数据
     */
    private List<Da> daList = new ArrayList<Da>();

    public KDBaseAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public KDBaseAdapter(Context mContext, List<Da> list) {
        this.mContext = mContext;
        this.daList=list;
    }
    /***
     * 在内置的daList后面追加数据
     */
    public void append(List<Da> appedList) {
        if (daList==null) {
            daList=new ArrayList<Da>();
        }
        daList.addAll(appedList);
    }

    /***
     * 重新加载数据,把内置的数据全部替换
     */
    public void reLoad(List<Da> appedList) {
        try {
            if (daList==null) {
                daList=new ArrayList<Da>();
            }
            daList.clear();
            daList.addAll(appedList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return daList == null ? 0 : daList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return (daList != null && daList.size() >= arg0) ? daList.get(arg0) : null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }


    public Context getContext() {
        return mContext;
    }

    public List<Da> getDaList() {
        return daList;
    }

    public void setDaList(List<Da> daList) {
        this.daList = daList;
    }
    public void getBitmap(ImageView imageView, String url) {
        getBitmap(imageView,url,IMG_CIRCULAR_BEAD);
    }

    public void getBitmap(ImageView imageView, String url,int circularBead) {
        try {
            Glide.with(getContext())
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
                    .transform(new RoundTransformation(getContext() , circularBead))
                    .crossFade()//或者使用 dontAnimate() 关闭动画
                    .placeholder(R.mipmap.ic_launcher)//图片加载出来前，显示的图片
                    .error(R.mipmap.ic_launcher)//图片加载失败后，显示的图片
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
