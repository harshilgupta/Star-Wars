package com.example.star_wars.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.star_wars.R
import com.example.star_wars.adapter.GridViewAdapter
import com.example.star_wars.databinding.FilterSheetBinding
import com.example.star_wars.databinding.FragmentHomeBinding
import com.example.star_wars.utils.NetworkResult
import com.example.star_wars.utils.isNetworkAvailable
import com.example.star_wars.viewmodels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Fragment used to display the Grid list of the People Data received via API.
 * @author by Harshil Gupta
 *
 * @property bottomSheetDialog is the custom Bottom Sheet Dialog used to show the filter and sorting screen
 * @property currentSort sets default sorting i.e. None
 * @property currentFilter sets default sorting i.e. All
 * @property totalRecords is the total number of people records provided by the api, it is used to calculate max pages for pagination
 * @property maxPages is the maximum number of pages that can be called to retrieve all the records in the database
 * @property currentPage denotes the current page number for API Pagination
 * @property layoutManager is used to set GridView and Endless Scroller to the recycler manager
 *
 */

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bottomSheetBinding: FilterSheetBinding
    private var currentSort = "None"
    private var currentFilter = "All"
    private lateinit var adapter: GridViewAdapter
    private var totalRecords = 0
    private var maxPages = 0
    private var currentPage = 1
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setObservers()
        callPeopleApi()
        setListeners()
        setUpAdapters()
    }

    private fun initialize() {
        bottomSheetDialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
        bottomSheetBinding = FilterSheetBinding.inflate(layoutInflater, null, false)
    }

    private fun setObservers() {
        viewModel.allPeopleData.observe(requireActivity()) { response ->
            when (response) {
                is NetworkResult.Success<*> -> {
                    Log.d("++++++++++++++++++++", "${response.data}")
                    binding.progressBar.visibility = View.GONE
                    updateList()
                    totalRecords = response.data?.count!!
                    maxPages = totalRecords / 10 + if (totalRecords % 10 > 0) 1 else 0
                }

                is NetworkResult.Error<*> -> {
                    Log.d("++++++++++++++++++++", Gson().toJson(response.data))
                    Toast.makeText(requireActivity(), response.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * @return this function is used to call the API and retrieve the data from the internet if there is any active connection.
     * If there is no active connection, it shows the data locally saved via room database.
     */
    private fun callPeopleApi() {
        binding.progressBar.visibility = View.VISIBLE
        if (isNetworkAvailable()) {
            viewModel.fetchAllUsers(currentPage)
        } else {
            Toast.makeText(
                requireActivity(), "No Internet Available\nShowing from local", Toast.LENGTH_LONG
            ).show()
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.showSaved()
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                    updateList()
                }

            }
        }
    }

    private fun updateList() {
        adapter.setUserList(viewModel.peopleList)
    }

    private fun setListeners() {
        binding.btnFilterSheet.setOnClickListener {
            openFilterSheet()
        }
    }

    /**
     * @return openFilterSheet() function is used to open the BottomSheetDialog and configure it's different properties.
     */
    private fun openFilterSheet() {
        bottomSheetDialog.setCancelable(true)
        when (currentSort) {
            "Alphabetical Order" -> bottomSheetBinding.radioAlphabetical.isChecked = true
            "Date Created" -> bottomSheetBinding.radioDateCreated.isChecked = true
            "Date Updated" -> bottomSheetBinding.radioDateUpdated.isChecked = true
            "None" -> bottomSheetBinding.radioNone.isChecked = true
        }
        when (currentFilter) {
            "Male" -> bottomSheetBinding.radioMale.isChecked = true
            "Female" -> bottomSheetBinding.radioFemale.isChecked = true
            "All" -> bottomSheetBinding.radioAll.isChecked = true
        }
        bottomSheetBinding.btnApply.setOnClickListener {
            when (bottomSheetBinding.groupSort.checkedRadioButtonId) {
                R.id.radioAlphabetical -> {
                    currentSort = "Alphabetical Order"
                }

                R.id.radioDateCreated -> {
                    currentSort = "Date Created"
                }

                R.id.radioDateUpdated -> {
                    currentSort = "Date Updated"
                }

                R.id.radioNone -> {
                    currentSort = "None"
                }
            }
            when (bottomSheetBinding.groupFilter.checkedRadioButtonId) {
                R.id.radioAll -> {
                    currentFilter = "All"
                }

                R.id.radioMale -> {
                    currentFilter = "Male"
                }

                R.id.radioFemale -> {
                    currentFilter = "Female"
                }
            }
            bottomSheetDialog.cancel()
        }
        bottomSheetBinding.tvClear.setOnClickListener {
            bottomSheetBinding.groupSort.check(R.id.radioNone)
            bottomSheetBinding.groupFilter.check(R.id.radioAll)
        }
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun setUpAdapters() {
        adapter = GridViewAdapter()
        layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        setUpScrollListener()
    }

    private fun setUpScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastCompletelyVisibleItemPosition() == viewModel.peopleList.size - 1) {
                    if (currentPage < maxPages) {
                        currentPage++
                        binding.progressBar.visibility = View.VISIBLE
                        callPeopleApi()
                    }
                }
            }
        })
    }

}