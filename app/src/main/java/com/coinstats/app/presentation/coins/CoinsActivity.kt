package com.coinstats.app.presentation.coins

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.coinstats.app.R
import com.coinstats.app.databinding.ActivityCoinsBinding
import com.coinstats.app.domain.model.Coin
import com.coinstats.app.util.SingleLayoutAdapter
import com.coinstats.app.util.base_classes.BaseViewBindingActivity

class CoinsActivity : BaseViewBindingActivity<ActivityCoinsBinding>() {
    override fun inflateViewBinding(layoutInflater: LayoutInflater): ActivityCoinsBinding {
        return ActivityCoinsBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        SingleLayoutAdapter<Coin>(
            itemLayoutId = R.layout.item_coin,
            bindViewHandler = {v,d ->
                v.findViewById<TextView>(R.id.textViewName).text = "test"
            },
            onClickHandler = {_,_ ->

            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.recyclerView.adapter = adapter
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.data = (0..20).toList().map { Coin("","","",0.0) }
    }
}