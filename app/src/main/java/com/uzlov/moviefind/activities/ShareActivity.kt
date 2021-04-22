package com.uzlov.moviefind.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.uzlov.moviefind.R
import com.uzlov.moviefind.repository.ContactRepository
import com.uzlov.moviefind.ui.MyItemDecorator
import kotlinx.coroutines.runBlocking


class ShareActivity : AppCompatActivity() {
    private val contactRepository by lazy { ContactRepository(context = this) }
    private val REQUEST_CODE: Int = 101

    private var nameFilm = ""
    private var urlFilm = ""

    private val callback = object : ClickListener {
        override fun click(name: String, pos: Int) {
            sendSms(name)
        }
    }

    private fun sendSms(name: String) {
        contactRepository.getNumberByName(name).observe(this, {phone->
            val uri: Uri = Uri.parse("smsto:${phone}")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "Посмотри какой фильм нашел! " +
                    "Называется ${nameFilm}." +
                    " Ссылка $urlFilm")
            startActivity(intent)
        })

    }

    private val adapterContact by lazy { ContactAdapter(callback) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_layout)

        intent?.run {
            nameFilm = getStringExtra("key_name") ?: ""
            urlFilm = getStringExtra("key_url") ?: ""
        }

        findViewById<RecyclerView>(R.id.recyclerViewContact).apply {
            layoutManager = LinearLayoutManager(this@ShareActivity)
            adapter = adapterContact
            addItemDecoration(MyItemDecorator(RecyclerView.VERTICAL))
        }

        checkPermission()
    }

    private fun checkPermission() {
        when (ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_CONTACTS
        )) {
            PackageManager.PERMISSION_GRANTED -> {

                runBlocking {
                    contactRepository.getContacts().observe(this@ShareActivity, { result ->
                        adapterContact.setContacts(result)
                    })
                }
            }
            PackageManager.PERMISSION_DENIED -> {
                requestPermission()
            }
            else -> requestPermission()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), REQUEST_CODE)
        } else {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                    // get contacts
                    runBlocking {
                        contactRepository.getContacts().observe(this@ShareActivity, {
                            adapterContact.setContacts(it)
                        })
                    }
                } else {
                    showAlertForPermission()
                }
            }
        }
    }

    private fun showAlertForPermission() {
        AlertDialog.Builder(this)
            .setTitle("Доступ к контактам")
            .setMessage("Для того чтоб поделиться фильмом с друзьями, программе нужен доступ к контактам")
            .setPositiveButton("Предоставить доступ") { _, _ ->
                requestPermission()
            }
            .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}

interface ClickListener {
    fun click(name: String, pos: Int)
}

class ContactAdapter(val listener: ClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    private val contacts = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ContactHolder(view)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(contact = contacts[position])
    }

    fun setContacts(listContacts: List<String>) {
        contacts.run {
            clear()
            addAll(listContacts)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = contacts.size

    inner class ContactHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvName = view.findViewById<MaterialTextView>(R.id.tvNameContact)

        fun bind(contact: String) {
            tvName.text = contact
            itemView.setOnClickListener {
                listener.click(contact, adapterPosition)
            }
        }
    }
}