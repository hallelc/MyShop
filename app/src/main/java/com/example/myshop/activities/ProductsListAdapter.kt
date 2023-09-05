package com.example.myshop.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.R
import com.example.myshop.models.Product
import com.squareup.picasso.Picasso

class ProductsListAdapter (private val data: List<Product>) :
        RecyclerView.Adapter<ProductsListAdapter.ViewHolder>()  {
    private lateinit var mListener: onItemClickListener;

    interface onItemClickListener {
        fun onItemClick(position : Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(val itemView: View, clickListener: onItemClickListener):
    RecyclerView.ViewHolder(itemView) {
        var product : Product? = null;

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(bindingAdapterPosition)
                }
            }

        fun bind(product: Product){
            val title = itemView.findViewById<TextView>(R.id.title);
            val image = itemView.findViewById<ImageView>(R.id.imageView);
            val price = itemView.findViewById<TextView>(R.id.price);

            title.text = product.title;
            price.text = "$${product.price.toString()}";
            Picasso.get().load(product.image).into(image)
        }
    }
}