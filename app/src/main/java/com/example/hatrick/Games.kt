package com.example.hatrick

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Games.newInstance] factory method to
 * create an instance of this fragment.
 */
class Games : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v : View = inflater.inflate(R.layout.fragment_games, container, false)
        val fCArd = v.findViewById<CardView>(R.id.footballCard)
        val bCArd = v.findViewById<CardView>(R.id.basketballCard)
        val tCArd = v.findViewById<CardView>(R.id.tennisCard)
        val hCArd = v.findViewById<CardView>(R.id.handballCard)
        val bmCArd = v.findViewById<CardView>(R.id.badmintonCard)
        val vCArd = v.findViewById<CardView>(R.id.volleyballCard)
        fCArd.setOnClickListener {
            val intent = Intent(this@Games.requireContext(),GamesListActivity::class.java)
            intent.putExtra("card","Football")
            intent.putExtra("act","all")
            startActivity(intent)
        }
        bCArd.setOnClickListener {
            val intent = Intent(this@Games.requireContext(),GamesListActivity::class.java)
            intent.putExtra("card","Basketball")
            intent.putExtra("act","all")
            startActivity(intent)
        }
        tCArd.setOnClickListener {
            val intent = Intent(this@Games.requireContext(),GamesListActivity::class.java)
            intent.putExtra("card","Tennis")
            intent.putExtra("act","all")
            startActivity(intent)
        }
        hCArd.setOnClickListener {
            val intent = Intent(this@Games.requireContext(),GamesListActivity::class.java)
            intent.putExtra("card","Handball")
            intent.putExtra("act","all")
            startActivity(intent)
        }
        bmCArd.setOnClickListener {
            val intent = Intent(this@Games.requireContext(),GamesListActivity::class.java)
            intent.putExtra("card","Badminton")
            intent.putExtra("act","all")
            startActivity(intent)
        }
        vCArd.setOnClickListener {
            val intent = Intent(this@Games.requireContext(),GamesListActivity::class.java)
            intent.putExtra("card","Volleyball")
            intent.putExtra("act","all")
            startActivity(intent)
        }
        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Games.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Games().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}