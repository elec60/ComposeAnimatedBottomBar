package hashem.mousavi.animatedbottombar.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class Tab(val icon: ImageVector) {
    Menu(Icons.Default.Menu),
    Favorite(Icons.Default.Favorite),
    Home(Icons.Default.Home),
    Search(Icons.Default.Search),
    Settings(Icons.Default.Settings);

    val count: Int
        get() = values().size
}