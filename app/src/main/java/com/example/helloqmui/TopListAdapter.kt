package com.example.helloqmui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.helloqmui.databinding.ItemGridBinding
import com.example.helloqmui.databinding.ItemListHorBinding

class TopListAdapter(mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_3 = 3
        const val VIEW_TYPE_6 = 6
    }

    private val mInflater = LayoutInflater.from(mContext)

    private val testData: List<String?> = arrayListOf(
        "Helps", "Maintain", "Liver",
        "Health", "Function", "Supports",
        "Healthy", "Fat", "Metabolism",
        "Nuturally", "Bracket", "Refrigerator",
        "Bathtub", "Wardrobe", "Comb"
    ).shuffled()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_3) {
            TopGridHolder(ItemGridBinding.inflate(mInflater))
        } else {
            TopListHolder(ItemListHorBinding.inflate(mInflater))
        }
    }

    override fun getItemCount(): Int = testData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //holder.binding.tvItemTitle.text = "TOP LIST:"+testData[position]
        //Log.e("TAG","item[$position]被渲染")
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % 3 == 0) {
            VIEW_TYPE_6
        } else
            VIEW_TYPE_3
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) {
            manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if ((position + 1) % 3 == 0) {
                        return 6
                    }
                    return 3
                }
            }
        }
    }
}

class TopGridHolder(
    binding: ItemGridBinding
) : RecyclerView.ViewHolder(binding.root)

class TopListHolder(
    binding: ItemListHorBinding
) : RecyclerView.ViewHolder(binding.root)