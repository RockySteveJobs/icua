package iCua.Data.Adapters;

import iCua.Activities.R;
import iCua.Activities.R.drawable;
import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{

   private Context mContext;
   private Integer[] lImagenes = {R.drawable.icon,R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5,R.drawable.i6,R.drawable.i7};

   public ImageAdapter(Context context){
      mContext = context;
   }
   
   public int getCount() {
      return lImagenes.length;
   }

   public Object getItem(int position) {
      return lImagenes[position];
   }

   public long getItemId(int position) {      
      return position;
   }

   public View getView(int position, View convertView, ViewGroup parent) {
      
      ImageView i = new ImageView(mContext);

        i.setImageResource(lImagenes[position]);
        
        //i.setLayoutParams(new ViewGroup.LayoutParams(45, 45));
   //     i.setAdjustViewBounds(false);
        
       i.setPadding(12, 12, 12, 12);
        return i;
   } 
   }
