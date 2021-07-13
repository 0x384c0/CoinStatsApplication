package com.coinstats.app.util.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class SingleViewBindingAdapter<T, B : ViewBinding>(
    private val inflate: (ViewGroup) -> B,
    private val itemViewHolderFactory: (B) -> BaseVBItemViewHolder<T, B>
) : RecyclerView.Adapter<BaseVBItemViewHolder<T, B>>() {

    //region Adapter logic
    var data: List<T>
        set(value) {
            _data = value
            notifyDataSetChanged()
        }
        get() = _data
    private var _data: List<T> = listOf()


    override fun getItemCount(): Int {
        return _data.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVBItemViewHolder<T, B> {
        return itemViewHolderFactory(inflate(parent))
    }

    override fun onBindViewHolder(itemViewHolder: BaseVBItemViewHolder<T, B>, position: Int) {
        itemViewHolder.setup(_data[position])
    }
    //endregion


    //region Implementation with handlers
    constructor(
        inflate: (ViewGroup) -> B,
        bindViewHandler: (binding: B, data: T) -> Unit,
        onClickHandler: ((position: Int, data: T) -> Unit)? = null
    ) : this(
        inflate = inflate,
        itemViewHolderFactory = {
            VBItemViewHolderWithHandler(
                it,
                bindViewHandler,
                onClickHandler
            )
        })
    //endregion
}

abstract class BaseVBItemViewHolder<T, B : ViewBinding>(var view: View) :
    RecyclerView.ViewHolder(view) {
    abstract fun setup(data: T)
}

class VBItemViewHolderWithHandler<T, B : ViewBinding>(
    private val binding: B,
    private val bindViewHandler: (binding: B, data: T) -> Unit,
    private val onClickHandler: ((position: Int, data: T) -> Unit)? = null
) : BaseVBItemViewHolder<T, B>(binding.root), View.OnClickListener {
    init {
        view.setOnClickListener(this)
    }

    var data: T? = null
    override fun setup(data: T) {
        this.data = data
        bindViewHandler.invoke(binding, data!!)
    }

    override fun onClick(p0: View?) {
        onClickHandler?.invoke(adapterPosition, data!!)
    }
}