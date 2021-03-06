package com.fiec.eciot.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fiec.eciot.R;
import com.fiec.eciot.databinding.ItemObjectBinding;
import com.fiec.eciot.models.Category;
import com.fiec.eciot.models.ObjectModel;

import java.util.List;

import io.realm.Realm;

public class ObjectAdapter extends RecyclerView.Adapter<ObjectAdapter.ChildViewHolder> {


    private Context mContext;
    private final LayoutInflater mInflater;
    private List<ObjectModel> mObjectss;

    public ObjectAdapter(Context context, List<ObjectModel> mObjects){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mObjectss = mObjects;

    }

    @NonNull
    @Override
    public ObjectAdapter.ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemObjectBinding binding = ItemObjectBinding.inflate(mInflater, parent, false);
        return new ObjectAdapter.ChildViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectAdapter.ChildViewHolder holder, int position) {
        final ObjectModel objectModel = mObjectss.get(position);
        holder.bind(objectModel);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return  mObjectss.size();
    }

    class ChildViewHolder extends RecyclerView.ViewHolder {

        private ItemObjectBinding mBinding;

        ChildViewHolder(ItemObjectBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(ObjectModel objectModel) {
            mBinding.setObject(objectModel);
            setImagen(String.valueOf(objectModel.getCategoria()));
            Realm realm = Realm.getDefaultInstance();
            try{
                Category category = realm.where(Category.class).
                        equalTo("id",objectModel.getCategoria()).findFirst();
                mBinding.category.setText(category.getNombre());
                if (objectModel.isAcerto()){
                    mBinding.state.setText("Acertó");
                    mBinding.state.setTextColor(Color.GREEN);
                } else {
                    mBinding.state.setText("Falló");
                    mBinding.state.setTextColor(Color.RED);
                }

            } catch (Exception e){
                e.getMessage();
            }


        }
        public void setImagen(String idCategoria){

            final ImageView image = mBinding.image;

            if(idCategoria.equals("2")) {
                image.setImageResource(R.drawable.ic_phone);
            }
            else if(idCategoria.equals("5")){
                image.setImageResource(R.drawable.cuaderno);
            }
            else if(idCategoria.equals("7")){
                image.setImageResource(R.drawable.ic_glass);
            }
            else if(idCategoria.equals("8")){
                image.setImageResource(R.drawable.ic_laptop);
            }
            else if(idCategoria.equals("9")){
                image.setImageResource(R.drawable.ic_plate);
            }
            else if(idCategoria.equals("10")){
                image.setImageResource(R.drawable.ic_unknown);
            }

        }

    }




}
