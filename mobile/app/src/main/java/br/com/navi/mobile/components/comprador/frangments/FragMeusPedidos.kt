package br.com.navi.mobile.components.comprador.frangments

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.content.res.ResourcesCompat.getColorStateList
import androidx.fragment.app.Fragment
import br.com.navi.mobile.R
import br.com.navi.mobile.models.Pedido
import br.com.navi.mobile.services.PedidoService
import kotlinx.android.synthetic.main.activity_comprador_frag_pedidos.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragMeusPedidos():Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_comprador_frag_pedidos,container,false)
        getAllPedidos()
        return view
    }

    fun getAllPedidos() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://navi--api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val requestsPedido = retrofit.create(PedidoService::class.java)
        val callPedidosComprador = requestsPedido.getPedidosComprador("34857844898")

        callPedidosComprador.enqueue(object : Callback<List<Pedido>> {
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>) {
                response.body()?.forEach {
                    val newTv = TextView(context)

                    newTv.text = "Numero do Pedido: ${it.numeroDoPedido} \nDescrição: ${it.descricao} \nPreço: ${it.preco}"
                    newTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                    newTv.setTextColor(Color.parseColor("#2196F3"))
                    newTv.setBackgroundColor(Color.parseColor("#FFFFFF"))

                    content_pedidos.addView(newTv)
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}