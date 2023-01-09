package com.example.gawekerjo.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gawekerjo.R
import com.example.gawekerjo.database.AppDatabase
import com.example.gawekerjo.databinding.ActivityFriendListBinding
import com.example.gawekerjo.databinding.FragmentFollowBinding
import com.example.gawekerjo.databinding.FragmentOffersBinding
import com.example.gawekerjo.model.Offer.OfferItem
import com.example.gawekerjo.model.follow.FollowItem
import com.example.gawekerjo.model.user.UserItem
import com.example.gawekerjo.repository.FollowRepository
import com.example.gawekerjo.repository.OfferRepository
import com.example.gawekerjo.view.adapter.AddFriendAdapter
import com.example.gawekerjo.view.adapter.FollowAdapter
import com.example.gawekerjo.view.adapter.FollowAdapter2
import com.example.gawekerjo.view.adapter.RVAdapterJob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowFragment(var FollowAdapter2:FollowAdapter2) : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var FollowAdapter : FollowAdapter
    private lateinit var b : FragmentFollowBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var ctx : Context
    private var listOffer : List<OfferItem> = listOf()
    lateinit var rvAll: RecyclerView

    private lateinit var offerRepo : OfferRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvAll = view.findViewById(R.id.rv)
        val verticalLayoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)
        rvAll.adapter = FollowAdapter2
        Log.d("CCD", "Ini nyoba di adapter size e : " + FollowAdapter2.followList.size.toString())
    }
}