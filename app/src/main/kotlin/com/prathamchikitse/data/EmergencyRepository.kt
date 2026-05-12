package com.prathamchikitse.data

import android.content.Context
import com.prathamchikitse.model.Emergency
import com.prathamchikitse.model.Hospital
import com.prathamchikitse.utils.JsonLoader
import org.json.JSONObject

object EmergencyRepository {

    private var emergencyData: JSONObject = JSONObject()
    // =========================
    // LOAD LANGUAGE FILE
    // =========================
    fun loadLanguage(context: Context, langCode: String) {

        val file = when (langCode) {
            "hi" -> "emergency_hi.json"
            "kn" -> "emergency_kn.json"
            else -> "emergency_en.json"
        }

        emergencyData = JsonLoader.loadJson(context, file)
    }

    // =========================
    // GET SINGLE EMERGENCY
    // =========================
    fun getEmergencyById(id: String): Emergency? {
        val obj = emergencyData?.optJSONObject(id) ?: return null

        return Emergency(
            id = id,
            title = obj.optString("title", id),
            emoji = obj.optString("emoji", "🚨"),
            colorHex = obj.optString("colorHex", "#2E7D32"),

            steps = jsonArrayToList(obj.optJSONArray("steps")),
            dos = jsonArrayToList(obj.optJSONArray("dos")),
            donts = jsonArrayToList(obj.optJSONArray("donts"))
        )
    }

    // =========================
    // GET ALL EMERGENCIES
    // (for home screen list)
    // =========================
    fun getAllEmergencies(): List<Emergency> {
        val list = mutableListOf<Emergency>()
        val keys = emergencyData?.keys() ?: return emptyList()

        while (keys.hasNext()) {
            val key = keys.next()
            getEmergencyById(key)?.let { list.add(it) }
        }

        return list
    }

    // =========================
    // HOSPITAL DATA (STATIC)
    // =========================
    // =========================
// HOSPITAL DATA
// =========================
    val hospitals: List<Hospital>
        get() {

            val language =
                emergencyData.optString("language")

            return when (language) {

                // ================= HINDI =================
                "hi" -> listOf(

                    Hospital(
                        name = "विक्टोरिया अस्पताल",
                        phone = "080-26701150",
                        address = "फोर्ट रोड, बेंगलुरु - 560002",
                        distance = "2.3 किमी",
                        type = "सरकारी"
                    ),

                    Hospital(
                        name = "सेंट जॉन्स मेडिकल कॉलेज अस्पताल",
                        phone = "080-22065000",
                        address = "कोरमंगला, बेंगलुरु - 560034",
                        distance = "4.1 किमी",
                        type = "निजी"
                    ),

                    Hospital(
                        name = "बॉवरिंग अस्पताल",
                        phone = "080-25470537",
                        address = "शिवाजीनगर, बेंगलुरु - 560001",
                        distance = "3.7 किमी",
                        type = "सरकारी"
                    )
                )

                // ================= KANNADA =================
                "kn" -> listOf(

                    Hospital(
                        name = "ವಿಕ್ಟೋರಿಯಾ ಆಸ್ಪತ್ರೆ",
                        phone = "080-26701150",
                        address = "ಫೋರ್ಟ್ ರಸ್ತೆ, ಬೆಂಗಳೂರು - 560002",
                        distance = "2.3 ಕಿಮೀ",
                        type = "ಸರ್ಕಾರಿ"
                    ),

                    Hospital(
                        name = "ಸೇಂಟ್ ಜಾನ್ಸ್ ಮೆಡಿಕಲ್ ಕಾಲೇಜ್ ಆಸ್ಪತ್ರೆ",
                        phone = "080-22065000",
                        address = "ಕೋರಮಂಗಲ, ಬೆಂಗಳೂರು - 560034",
                        distance = "4.1 ಕಿಮೀ",
                        type = "ಖಾಸಗಿ"
                    ),

                    Hospital(
                        name = "ಬೌರಿಂಗ್ ಆಸ್ಪತ್ರೆ",
                        phone = "080-25470537",
                        address = "ಶಿವಾಜಿನಗರ, ಬೆಂಗಳೂರು - 560001",
                        distance = "3.7 ಕಿಮೀ",
                        type = "ಸರ್ಕಾರಿ"
                    )
                )

                // ================= ENGLISH =================
                else -> listOf(

                    Hospital(
                        name = "Victoria Hospital",
                        phone = "080-26701150",
                        address = "Fort Rd, Bengaluru - 560002",
                        distance = "2.3 km",
                        type = "Government"
                    ),

                    Hospital(
                        name = "St. John's Medical College Hospital",
                        phone = "080-22065000",
                        address = "Koramangala, Bengaluru - 560034",
                        distance = "4.1 km",
                        type = "Private"
                    ),

                    Hospital(
                        name = "Bowring Hospital",
                        phone = "080-25470537",
                        address = "Shivajinagar, Bengaluru - 560001",
                        distance = "3.7 km",
                        type = "Government"
                    )
                )
            }
        }

    // =========================
    // JSON HELPER
    // =========================
    private fun jsonArrayToList(array: org.json.JSONArray?): List<String> {
        if (array == null) return emptyList()

        return List(array.length()) { index ->
            array.optString(index)
        }
    }
}