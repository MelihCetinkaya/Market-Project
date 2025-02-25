package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.fragments.ProfileFragment
import com.example.myapplication.fragments.MarketsFragment
import com.example.myapplication.fragments.SearchFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CustomerContentActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_content)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        // Set up the adapter
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Link the TabLayout and the ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Profile"
                    tab.setIcon(android.R.drawable.ic_menu_myplaces)
                }
                1 -> {
                    tab.text = "Markets"
                    tab.setIcon(android.R.drawable.ic_menu_agenda)
                }
                2 -> {
                    tab.text = "Search"
                    tab.setIcon(android.R.drawable.ic_menu_search)
                }
            }
        }.attach()
    }

    private inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProfileFragment()
                1 -> MarketsFragment()
                2 -> SearchFragment()
                else -> throw IllegalStateException("Invalid position $position")
            }
        }
    }
} 