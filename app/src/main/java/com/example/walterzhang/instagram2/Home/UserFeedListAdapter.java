package com.example.walterzhang.instagram2.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.walterzhang.instagram2.Models.Like;
import com.example.walterzhang.instagram2.R;
import com.example.walterzhang.instagram2.models.Photo;
import com.example.walterzhang.instagram2.utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link //DummyContent.DummyItem} and makes a call to the
 * specified {@link fragment_post_list.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UserFeedListAdapter extends RecyclerView.Adapter<UserFeedListAdapter.MyViewHolder> {

    private static final String TAG = "UserFeedListAdapter";

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private DatabaseReference mReference;
    private String username;

    private List<Photo> mDataset;

    static class MyViewHolder extends RecyclerView.ViewHolder {
//        com.example.walterzhang.instagram2.models.User user = new com.example.walterzhang.instagram2.models.User();
//        StringBuilder users;
//        CircleImageView profileImage;
//        TextView username;
        ImageView image;
        ImageView mHeartWhite, mHeartRed;

        private FirebaseMethods mFirebaseMethods;
        Photo photo;

//        boolean likedByCurrentUser;
//        Photo photo;
//        GestureDetector detector;

        View view;

        public MyViewHolder(View v) {
            super(v);
            view = v;

            mFirebaseMethods = new FirebaseMethods(v.getContext());

            image = v.findViewById(R.id.imageView_photo);

            mHeartWhite = (ImageView) view.findViewById(R.id.button_notLiked);
            mHeartRed = (ImageView) view.findViewById(R.id.button_liked);

            mHeartRed.setVisibility(View.GONE);
            mHeartWhite.setVisibility(View.VISIBLE);

            mHeartWhite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: toggling like...");
                    onLikePostClicked();
                }
            });

            mHeartRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: toggling like...");
                    onLikePostClicked();
                }
            });
        }

        /* Toggle between photo liked and not liked */
        public void onLikePostClicked()
        {
            Log.d(TAG, "onLikePostClicked...");
            if (mHeartWhite.getVisibility() == View.VISIBLE) {
                mHeartWhite.setVisibility(View.GONE);
                mHeartRed.setVisibility(View.VISIBLE);
                //save the like information to the db:
                mFirebaseMethods.addNewLike(photo.getPhoto_id());
            }
            else {
                mHeartRed.setVisibility(View.GONE);
                mHeartWhite.setVisibility(View.VISIBLE);
                mFirebaseMethods.removeLike(photo.getPhoto_id());
            }
        }

        /* Check if the photo has been liked by the user and if yes, set heart to red */
        public void setHeartColor(final String photoId) {
            DatabaseReference myRef;
            FirebaseDatabase mFirebaseDatabase;
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            myRef = mFirebaseDatabase.getReference();

            Query query = myRef.child(view.getContext().getString(R.string.dbname_photos))
                    .child(photoId)
                    .child(view.getContext().getString(R.string.field_likes));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                        if (singleSnapshot.getValue(Like.class).getUser_id()
                                .equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            mHeartRed.setVisibility(View.VISIBLE);
                            mHeartWhite.setVisibility(View.GONE);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public UserFeedListAdapter(@NonNull Context context, int resource, @NonNull List<Photo> photos) {

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        mReference = FirebaseDatabase.getInstance().getReference();
        mDataset = photos;
    }

    @Override
    public UserFeedListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(this.mContext).load(mDataset.get(position).getImage_path()).into(holder.image);
        holder.photo = mDataset.get(position);
        //if the photo has been liked by the user then set the heart to red:
        holder.setHeartColor(holder.photo.getPhoto_id());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}