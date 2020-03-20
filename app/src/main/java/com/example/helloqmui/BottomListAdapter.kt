package com.example.helloqmui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.helloqmui.databinding.ItemBottomListBinding

class BottomListAdapter(mContext:Context) : RecyclerView.Adapter<BottomListHolder>() {

    private val mInflater = LayoutInflater.from(mContext)

    private val testData: List<String?> = arrayListOf(
        "Helps", "Maintain", "Liver",
        "Health", "Function", "Supports",
        "Healthy", "Fat", "Metabolism",
        "Nuturally", "Bracket", "Refrigerator",
        "Bathtub", "Wardrobe", "Comb",
        "Apron", "Carpet", "Bolster",
        "Pillow", "Cushion", "Wardrobe", "Comb",
        "Apron", "Carpet", "Bolster",
        "Pillow", "Cushion", "Wardrobe", "Comb",
        "Apron", "Carpet", "Bolster",
        "Pillow", "Cushion", "Wardrobe", "Comb",
        "Apron", "Carpet", "Bolster"
    ).shuffled()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomListHolder {
        return BottomListHolder(ItemBottomListBinding.inflate(mInflater))
    }

    override fun getItemCount(): Int = testData.size

    override fun onBindViewHolder(holder: BottomListHolder, position: Int) {
        holder.binding.tvItemTitle.text = "Bottom LIST:"+testData[position]
        //Log.e("TAG","item[$position]被渲染")
    }
}

class BottomListHolder(
    val binding: ItemBottomListBinding
) : RecyclerView.ViewHolder(binding.root) {

}