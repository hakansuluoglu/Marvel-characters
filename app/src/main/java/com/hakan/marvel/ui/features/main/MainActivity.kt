package com.hakan.marvel.ui.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelProvider
import com.hakan.marvel.R
import com.hakan.marvel.ui.features.main.characters.CharactersFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity(), LifecycleOwner  {

    private var mLifecycleRegistry: LifecycleRegistry? = null

    @Inject
    lateinit var vmFactory: SharedViewModelFactory

    @Inject
    lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mLifecycleRegistry = LifecycleRegistry(this)
        viewModel = ViewModelProvider(this, vmFactory).get(SharedViewModel::class.java)
        supportFragmentManager.commit(allowStateLoss = false) {
            val fragment =
                CharactersFragment()
            add(R.id.main_fragment_container, fragment, fragment.tag)
        }
    }

    override fun getLifecycle(): Lifecycle {
        return if (mLifecycleRegistry != null) {
            mLifecycleRegistry as LifecycleRegistry
        } else {
            mLifecycleRegistry = LifecycleRegistry(this)
            mLifecycleRegistry as LifecycleRegistry
        }
    }
}
