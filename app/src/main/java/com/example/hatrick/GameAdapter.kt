package com.example.hatrick

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.squareup.picasso.Picasso

class GameAdapter(var c: Context, private val gameList : ArrayList<Reservation>, private val card: String, private val act: String) : RecyclerView.Adapter<GameAdapter.MyViewHolder>() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.game_item,
            parent,false)
        val viewField = itemView.findViewById<TextView>(R.id.viewField)
        val joinGame = itemView.findViewById<TextView>(R.id.joinGame)
        val totalprice = itemView.findViewById<LinearLayout>(R.id.totalPrice)
        val ppp = itemView.findViewById<LinearLayout>(R.id.PPP)
        val nop = itemView.findViewById<LinearLayout>(R.id.NOP)

        if (act == "history")
        {
            viewField.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            joinGame.layoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,0)
            totalprice.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            ppp.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0)
            nop.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0)
        }
        else if (act == "upcoming")
        {
            viewField.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            joinGame.layoutParams = LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,0)
            totalprice.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)
            ppp.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0)
            nop.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,0)
        }
        if (card=="Football")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#009900"))
        }
        if (card=="Basketball")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#FF5207"))
        }
        if (card=="Tennis")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#AAEE00"))
        }
        if (card=="Handball")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#023e7d"))
        }
        if (card=="Badminton")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#ae2012"))
        }
        if (card=="Volleyball")
        {
            viewField.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
            joinGame.setBackgroundColor(android.graphics.Color.parseColor("#4361ee"))
        }
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val game : Reservation = gameList[position]
        holder.fieldName.text = game.fieldName
        holder.stime.text = game.startTime
        holder.ftime.text = game.finishTime
        holder.date.text = game.reservationDate
        holder.pricePP.text = game.pricePerPerson.toString()
        holder.noPlayers.text = game.noplayers.toString()
        holder.totPrice.text = game.totalPrice.toString()
        holder.sporttype.text = game.sportType.toString()
        val fieldId = game.fieldID
        val resId = game.reservationID
        val price = game.pricePerPerson.toString().toFloat()
        val hours  = game.nohours.toString().toInt()
        val tot = price*hours
        val newTotPrice = ((game.totalPrice)?.plus(tot))
        val newNoPlayers = game.noplayers?.plus(1)
        val FieldData = FirebaseFirestore.getInstance()
        var capacity = 0
        FieldData.collection("Fields").whereEqualTo("fieldID",fieldId).get().addOnCompleteListener {
            if (it.isSuccessful) {
                for (doc in it.result!!) {
                    capacity = doc.data.getValue("capacity").toString().toInt()
                }
            }
        }
        holder.viewField.setOnClickListener {
            val intent = Intent(c, FieldInfo2::class.java)
            intent.putExtra("fieldID", fieldId)
            c.startActivity(intent)
        }
        holder.joinGame.setOnClickListener {
            if(game.noplayers!! < capacity ){
            holder.joinGame.setBackgroundColor(android.graphics.Color.parseColor("#a6a6a6"))
            holder.joinGame.isClickable = false
            val participation = Participant(resId, getCurrentUserID(), tot,
            "true", game.fieldName, game.sportType, newNoPlayers, game.reservationDate,
                game.startTime, game.finishTime)
            val UserFireData = FirebaseFirestore.getInstance()
            val currnetdoc = UserFireData.collection("Participant").document()
            currnetdoc.set(
                participation,
                SetOptions.merge()
            ).addOnSuccessListener {
                UserFireData.collection("Reservations").document("$resId").update(
                    "noplayers",newNoPlayers,"totalPrice",newTotPrice)
                holder.noPlayers.text = game.noplayers.toString()

            }.addOnFailureListener {
            }
        }else {
                val context = holder.itemView.context
                Toast.makeText(context, "Game is full", Toast.LENGTH_SHORT).show()
            }
            }
    }
    override fun getItemCount(): Int {
        return gameList.size
    }
    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val fieldName: TextView = itemView.findViewById(R.id.FieldName)
        val noPlayers : TextView = itemView.findViewById(R.id.numberOfplayers)
        val date : TextView = itemView.findViewById(R.id.date)
        val stime : TextView = itemView.findViewById(R.id.sTime)
        val ftime : TextView = itemView.findViewById(R.id.fTime)
        val pricePP : TextView = itemView.findViewById(R.id.pricePP)
        val totPrice : TextView = itemView.findViewById(R.id.fieldPrice)
        val viewField : Button = itemView.findViewById(R.id.viewField)
        val joinGame :Button = itemView.findViewById(R.id.joinGame)
        val sporttype:TextView = itemView.findViewById(R.id.SportType)
    }
}