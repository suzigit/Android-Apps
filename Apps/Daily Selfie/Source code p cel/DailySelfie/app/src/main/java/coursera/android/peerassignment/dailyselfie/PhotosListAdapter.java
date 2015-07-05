package coursera.android.peerassignment.dailyselfie;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by suzana on 7/3/2015.
 */
public class PhotosListAdapter extends BaseAdapter {

    private ArrayList<PhotoDescriptor> list = new ArrayList<PhotoDescriptor>();
    private static LayoutInflater inflater = null;
    private Context mContext;

    public PhotosListAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public void add(PhotoDescriptor listItem) {
        list.add(listItem);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View newView = convertView;
        ViewHolder holder;

        PhotoDescriptor curr = list.get(position);

        if (null == convertView) {
            holder = new ViewHolder();
            newView = inflater.inflate(R.layout.photo_descriptor_view, parent, false);
            holder.photoIcon = (ImageView) newView.findViewById(R.id.photo_icon);
            holder.photoName = (TextView) newView.findViewById(R.id.photo_name);
            newView.setTag(holder);

        } else {
            holder = (ViewHolder) newView.getTag();
        }

        // Get the dimensions of the View
        int targetW = holder.photoIcon.getLayoutParams().width;
        int targetH = holder.photoIcon.getLayoutParams().height;


        holder.photoIcon.setImageBitmap(PhotoDescriptor.setPic(targetW, targetH, curr.getPath()));
        holder.photoName.setText(curr.getName());

        return newView;
    }

    static class ViewHolder {
        ImageView photoIcon;
        TextView photoName;
    }

}


