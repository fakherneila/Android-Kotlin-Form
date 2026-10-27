package tn.tp.atelierandroid

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Compteurs de places (réinitialisés au redémarrage)
    private var placesG1 = 3
    private var placesG2 = 3
    private var placesG3 = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Récupérer les composants
        val etNom = findViewById<EditText>(R.id.etNom)
        val etPrenom = findViewById<EditText>(R.id.etPrenom)
        val etAge = findViewById<EditText>(R.id.etAge)
        val spGroupe = findViewById<Spinner>(R.id.spGroupe)
        val rgSupport = findViewById<RadioGroup>(R.id.rgSupport)
        val cbConfirmation = findViewById<CheckBox>(R.id.cbConfirmation)
        val btnAfficher = findViewById<Button>(R.id.btnAfficher)
        val btnEffacer = findViewById<Button>(R.id.btnEffacer)
        val tvResultat = findViewById<TextView>(R.id.tvResultat)
        val tvPlaces = findViewById<TextView>(R.id.tvPlaces)

        // Initialiser l'affichage des places
        updatePlaces(tvPlaces)

        // 2. Logique du bouton "Afficher la fiche"
        btnAfficher.setOnClickListener {
            val nom = etNom.text.toString().trim()
            val prenom = etPrenom.text.toString().trim()
            val age = etAge.text.toString().toIntOrNull()
            val groupe = spGroupe.selectedItem.toString()
            val modeTest = if (rgSupport.checkedRadioButtonId == R.id.rbEmulateur) "Émulateur" else "Téléphone"

            // Validation
            if (nom.isEmpty() || prenom.isEmpty()) {
                Toast.makeText(this, "Le nom et le prénom sont obligatoires", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (age == null || age < 1 || age > 120) {
                Toast.makeText(this, "L'âge doit être un nombre entre 1 et 120", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (groupe == "Choisir un groupe") {
                Toast.makeText(this, "Veuillez choisir un groupe", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!cbConfirmation.isChecked) {
                Toast.makeText(this, "Veuillez cocher la case de confirmation", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Vérification des places (Défi Bonus)
            val placesDisponibles = when (groupe) {
                "G1" -> placesG1
                "G2" -> placesG2
                "G3" -> placesG3
                else -> 0
            }

            if (placesDisponibles <= 0) {
                Toast.makeText(this, "Groupe complet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Décrémenter le compteur du groupe choisi
            when (groupe) {
                "G1" -> placesG1--
                "G2" -> placesG2--
                "G3" -> placesG3--
            }

            // Mettre à jour l'affichage des places
            updatePlaces(tvPlaces)

            // Affichage du récapitulatif
            tvResultat.text = """
                FICHE D'INSCRIPTION
                Nom : $nom
                Prénom : $prenom
                Âge : $age ans
                Groupe : $groupe
                Support : $modeTest
            """.trimIndent()
        }

        // 3. Logique du bouton "Effacer"
        btnEffacer.setOnClickListener {
            etNom.text.clear()
            etPrenom.text.clear()
            etAge.text.clear()
            spGroupe.setSelection(0)
            rgSupport.check(R.id.rbEmulateur)
            cbConfirmation.isChecked = false
            tvResultat.text = getString(R.string.resultat_initial)
            // Ne PAS réinitialiser les places
        }
    }

    // Fonction utilitaire pour mettre à jour le TextView
    private fun updatePlaces(tvPlaces: TextView) {
    val g1 = if (placesG1 == 0) "COMPLET" else "$placesG1"
    val g2 = if (placesG2 == 0) "COMPLET" else "$placesG2"
    val g3 = if (placesG3 == 0) "COMPLET" else "$placesG3"

    val fullText = "Places restantes : G1 = $g1 | G2 = $g2 | G3 = $g3"

    val spannable = android.text.SpannableString(fullText)
    val red = android.text.style.ForegroundColorSpan(android.graphics.Color.RED)

    // Colorer chaque occurrence de "COMPLET" en rouge
    var index = fullText.indexOf("COMPLET")
    while (index >= 0) {
        spannable.setSpan(
            red,
            index,
            index + "COMPLET".length,
            android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        index = fullText.indexOf("COMPLET", index + 1)
    }

    tvPlaces.text = spannable
}
}