import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nihonhistory.CelebrationActivity
import com.example.nihonhistory.R
import com.example.nihonhistory.helpers.AppDatabase
import com.example.nihonhistory.models.Celebrate
import com.example.nihonhistory.models.SelectedCelebrate
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CelebrateRecyclerViewAdapter(
    private val selectedItems: List<Celebrate>,
    private val email: String?,
    private val coroutineScope: CoroutineScope,
    private val items: List<Celebrate> = listOf()
) : RecyclerView.Adapter<CelebrateRecyclerViewAdapter.ViewHolder>() {
    private lateinit var context: Context
    private lateinit var db: AppDatabase

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val holidayNameTW: TextView = itemView.findViewById(R.id.holidayNameTW)
        val holidayDateTW: TextView = itemView.findViewById(R.id.holidayDateTW)
        val selectedIB: ImageView = itemView.findViewById(R.id.selectedIB)
        val celebrateIW: RelativeLayout = itemView.findViewById(R.id.imageBg)
        val goBtn: Button = itemView.findViewById(R.id.goBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_holiday, parent, false)
        context = parent.context
        db = AppDatabase.getDbInstance(context)
        return ViewHolder(view)
    }

    override fun getItemCount() = if(items.isEmpty()) selectedItems.size else items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem =if(items.isNotEmpty())  items[position] else selectedItems[position]
        holder.apply {
            holidayNameTW.text = currentItem.name
            holidayDateTW.text = if (currentItem.startDate == currentItem.endDate) {
                currentItem.startDate.toString()
            } else {
                "${currentItem.startDate} - ${currentItem.endDate}"
            }

            selectedIB.setImageResource(
                if (selectedItems.contains(currentItem)) R.drawable.ic_heart_selected
                else R.drawable.ic_heart_unselected
            )

            goBtn.setOnClickListener {
                val intent = Intent(context, CelebrationActivity::class.java)
                intent.putExtra("Holiday", Gson().toJson(currentItem))
                context.startActivity(intent)
            }

            selectedIB.setOnClickListener {
                coroutineScope.launch { // Используем переданный CoroutineScope для запуска корутины
                    val user = withContext(Dispatchers.IO) {
                        db.usersDao().getUserByEmail(email!!)
                    }
                    if (user != null) {
                        val selectedCelebrates = withContext(Dispatchers.IO) {
                            db.selectedCelebratesDao().getSelectedCelebrates(user.id!!)
                        }
                        val selectedCelebrate = SelectedCelebrate(null, user.id!!, currentItem.id!!)
                        if (!selectedCelebrates.contains(currentItem)) {
                            withContext(Dispatchers.IO) {
                                db.selectedCelebratesDao().addSelectedCelebrate(selectedCelebrate)
                            }
                            selectedIB.setImageResource(R.drawable.ic_heart_selected)
                        } else {
                            val selectedCelebrateId = withContext(Dispatchers.IO) {
                                db.selectedCelebratesDao().getSelectedCelebrateId(currentItem.id!!)
                            }
                            withContext(Dispatchers.IO) {
                                db.selectedCelebratesDao().deleteSelectedCelebrate(SelectedCelebrate(selectedCelebrateId, user.id!!, currentItem.id!!))
                            }
                            selectedIB.setImageResource(R.drawable.ic_heart_unselected)
                        }
                    }
                }
            }
        }
    }
}
