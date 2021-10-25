package com.wlc.smartwatch.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wlc.smartwatch.R
import com.wlc.smartwatch.databinding.RowItemDevicesBinding
import com.wlc.smartwatch.models.DevicesData

class DevicesAdapter() : RecyclerView.Adapter<DevicesAdapter.ItemViewHolder>() {

    private var list = mutableListOf<DevicesData>()

    inner class ItemViewHolder(val binding: RowItemDevicesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindTo(data: DevicesData) {
            binding.txtName.text = data.name
            binding.txtMac.text = data.macId
            binding.txtRssi.text = data.rssi.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_devices, parent, false)
        return ItemViewHolder(RowItemDevicesBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindTo(list[position])
    }

    override fun getItemCount() = list.size

    fun addData(item: DevicesData) {
        var repeat = false

        list.forEachIndexed { index, devicesData ->
            if (item.macId == devicesData.macId) {
                list.removeAt(index)
                repeat = true
                list.add(index, item)
            }
        }
        if (!repeat)
            list.add(item)

    }

    fun clear() {
        list.clear()
    }
}