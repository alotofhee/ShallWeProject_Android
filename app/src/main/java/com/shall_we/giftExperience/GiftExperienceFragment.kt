package com.shall_we.giftExperience

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonElement
import com.shall_we.ExperienceDetail.ExperienceDetailViewModel
import com.shall_we.R
import com.shall_we.base.BaseFragment
import com.shall_we.databinding.FragmentGiftExperienceBinding
import com.shall_we.dto.ReservationRequest
import com.shall_we.dto.ReservationStatus
import java.text.SimpleDateFormat
import java.util.Date


class GiftExperienceFragment : Fragment() {

    lateinit var experienceDetailViewModel: ExperienceDetailViewModel
    lateinit var reservationRequest: ReservationRequest
    private var experienceGiftId:Int=1
    private var persons:Int=2
    var selectedDate: String? = null
    private lateinit var binding: FragmentGiftExperienceBinding
    private var receiverName:String="땡땡땡"
    private var phoneNumber:String="01000000000"
    private var imageKey:String="?"
    private var invitationComment: String="환영해!"
    private var reservationStatus: ReservationStatus=ReservationStatus.BOOKED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGiftExperienceBinding.inflate(inflater, container, false)  // Binding 객체 초기화

        arguments?.let {


            experienceGiftId = it.getInt("id") // id 키로 giftid 값을 불러와 저장하게 됩니다.
            persons=it.getInt("persons")
            selectedDate=it.getString("Date")

        }


                binding.giftreserveEdittext01.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                setEditTextBackground(binding.giftreserveEdittext01, s)
                // 수정된 부분
            }
        })

        binding.giftreserveEdittext02.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                setEditTextBackground(binding.giftreserveEdittext02, s)
                Log.d("receivername",receiverName)
                receiverName = s.toString()
            }
        })
        binding.giftreserveEdittext03.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                setEditTextBackground(binding.giftreserveEdittext03, s)
                invitationComment = s.toString()

            }
        })
        binding.giftreserveEdittext04.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                setEditTextBackground(binding.giftreserveEdittext04, s)
            }
        })
        binding.giftreserveEdittext05.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                setEditTextBackground(binding.giftreserveEdittext05, s)
            }
        })



        val numdata = listOf(" ", "010", "02", "031", "000")

        // 스피너 어댑터 생성
        val spinneradapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, numdata)

        // 스피너 드롭다운 레이아웃 설정
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // 스피너에 어댑터 설정
        binding.spinner.adapter = spinneradapter

        // 스피너 아이템 선택 리스너 설정
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 선택된 아이템 처리
                val selectedItem = numdata[position]
                binding.spinner.setBackgroundResource(R.drawable.edittext_design)
                Log.d("spinner", "Selected: $selectedItem")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.spinner.setBackgroundResource(R.drawable.edittext_design02)
            }
        }
        // 모든 EditText들을 담는 리스트
        val editTextList = listOf(
            binding.giftreserveEdittext01,
            binding.giftreserveEdittext02,
            binding.giftreserveEdittext03,
            binding.giftreserveEdittext04,
            binding.giftreserveEdittext05
        )

        // EditText들의 텍스트가 변경될 때마다 호출되는 리스너
        val editTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // 모든 EditText들이 채워져 있는지 확인
                val allFilled = editTextList.all { it.text.toString().isNotEmpty() }

                // 버튼 디자인 변경
                if (allFilled) {
                    binding.giftreserveBtn02.setBackgroundResource(R.drawable.btn_payment)
                    binding.giftreserveBtn02.isClickable = true
                } else {
                    binding.giftreserveBtn02.setBackgroundResource(R.drawable.btn_pay)
                    binding.giftreserveBtn02.isClickable=false
                }
            }
        }

        // 모든 EditText들에 리스너 추가
        editTextList.forEach { it.addTextChangedListener(editTextWatcher) }






        binding.giftreserveBtn02.setOnClickListener(){
           Log.d("clicked","clicked")
            binding.giftreserveBtn02.visibility = View.GONE
            binding.giftreserveBtn01.visibility=View.GONE
           // binding.exgiftBtn02.visibility=View.GONE

            binding.giftreserveEdittext03.visibility=View.GONE

            binding.giftreserveBtn02.visibility=View.GONE
            binding.giftreserveBtn01.visibility=View.GONE


            val giftFragment = GiftFragment() // 전환할 프래그먼트 인스턴스 생성
            val bundle = Bundle()
            bundle.putInt("id", experienceGiftId) // 클릭된 아이템의 이름을 "title" 키로 전달
            bundle.putString("date",selectedDate)
            bundle.putInt("persons",persons)
            bundle.putString("receivername", receiverName) // 클릭된 아이템의 이름을 "title" 키로 전달
            bundle.putString("invitationComment",invitationComment)
            giftFragment.arguments = bundle


            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment,giftFragment, "gift")

            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commitAllowingStateLoss()
        }
        return binding.root
    }

    private fun setEditTextBackground(editText: EditText, s: Editable?) {
        if (s.isNullOrEmpty()) {
            editText.setBackgroundResource(R.drawable.edittext_design02)
        } else {
            editText.setBackgroundResource(R.drawable.edittext_design)
        }
    }


}
