package com.example.daggerandroiddemo.ui.main.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerandroiddemo.R
import com.example.daggerandroiddemo.ui.main.Resource
import com.example.daggerandroiddemo.util.VerticalSpaceItemDecoration
import com.example.daggerandroiddemo.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class PostsFragment : DaggerFragment() {
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var postRecyclerAdapter: PostRecyclerAdapter

    private lateinit var viewModel: PostsViewModel
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        viewModel = ViewModelProviders.of(this, providerFactory).get(PostsViewModel::class.java)
        initRecyclerView()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.observePosts().removeObservers(viewLifecycleOwner)
        viewModel.observePosts().observe(
            viewLifecycleOwner,
            Observer { listResource ->

                when (listResource.status) {
                    Resource.Status.LOADING -> {
                        Log.d("PostsFragment", "Loading")
                    }
                    Resource.Status.SUCCESS -> {
                        listResource.data?.let { postRecyclerAdapter.setPosts(it) }
                    }
                    Resource.Status.ERROR -> {
                        Log.d("PostsFragment", "Error" + listResource.message)
                    }
                }
            })

    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val itemDecoration = VerticalSpaceItemDecoration(15)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.adapter = postRecyclerAdapter
    }
}