package frame.base.com.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import frame.base.com.R;
import frame.base.com.base.KDBaseAdapter;
import frame.base.com.homepage.bean.User;

/**
 * Created by hantao on 2018/2/18.
 */
public class ShopListAdapter extends KDBaseAdapter<User> {

    private ItemListner itemListner;
    public interface ItemListner {
        public void Click(User user);

        public void Delete(User user);
    }

    public void setItemListner(ItemListner itemListner) {
        this.itemListner = itemListner;
    }

    public ShopListAdapter(Context context) {
        super(context);
    }

    public ShopListAdapter(Context context, List<User> list) {
        super(context, list);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_shop_list, null);
        }
        ViewHolder holder = getViewHolder(convertView);
        if (getDaList()==null) {
            return convertView;
        }
        final User user = getDaList().get(position);
        holder.tv_name.setText(user.getName());
        getBitmap(holder.iv_shop, user.getUrl());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListner != null && user != null) {
                    user.setName("张三");
                    itemListner.Click(user);
                }
            }
        });
        holder.iv_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListner != null) {
                    itemListner.Delete(user);
                }
            }
        });
        return convertView;
    }

    /**
     * 获得控件管理对象
     *
     * @param view
     * @return
     */
    private ViewHolder getViewHolder(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    /**
     * 控件管理类
     */
    private class ViewHolder {
        private TextView tv_name, tv_price, tv_price_discount, tv_sell_num;
        private ImageView iv_shop;

        ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_price_discount = (TextView) view.findViewById(R.id.tv_price_discount);
            tv_sell_num = (TextView) view.findViewById(R.id.tv_sell_num);
            iv_shop = (ImageView) view.findViewById(R.id.iv_shop);
        }
    }
}
