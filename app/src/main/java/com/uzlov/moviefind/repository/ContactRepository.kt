package com.uzlov.moviefind.repository

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactRepository(val context: Context) {

    fun getContacts(): LiveData<List<String>> {
        val result: MutableLiveData<List<String>> = MutableLiveData()
        val contacts = mutableListOf<String>()
        context.let {
            GlobalScope.launch(Dispatchers.IO) {
                val contentResolver = it.contentResolver
                val cursorWithContact = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC"
                )
                cursorWithContact.use { cursor ->
                    for (i in 0..(cursor?.count ?: 0)) {
                        if (cursor?.moveToPosition(i)!!) {
                            contacts.add(
                                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                            )
                        }
                    }
                    result.postValue(contacts)
                }
            }
        }
        return result
    }

    fun getNumberByName(name: String) : MutableLiveData<String>{
        val result: MutableLiveData<String> = MutableLiveData()
        var contractID = ""
        context.let {
            GlobalScope.launch(Dispatchers.IO) {
                val contentResolver = it.contentResolver
                val cursorID = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    "${ContactsContract.Contacts.DISPLAY_NAME} = '$name'",
                    null,
                    null
                )
                cursorID.use {
                    if (cursorID?.moveToFirst()!!){
                        contractID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
                    }
                }
                val cursorPhones = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = $contractID",
                    null,
                    null
                )

                cursorPhones.use {
                    while (cursorPhones?.moveToNext()!!){
                        result.postValue(it?.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                    }
                }
            }
        }
        return result
    }
}