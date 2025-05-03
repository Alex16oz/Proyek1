package com.example.proyek1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.proyek1.ui.theme.Proyek1Theme
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object UserProfile : Screen("user_profile")
    data object UserManagement : Screen("user_management")
    data object Attendance : Screen("attendance")
    data object Warehouse : Screen("warehouse")
    data object Schedule : Screen("schedule")
    data object SpareParts : Screen("spare_parts")
    data object RepairReports : Screen("repair_reports")
    data object DamageReports : Screen("damage_reports")
    data object AttendanceReports : Screen("attendance_reports")
    data object Settings : Screen("settings")
    data object About : Screen("about")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyek1Theme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "User Profile",
                            modifier = Modifier.size(80.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("User Name", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("user@example.com", fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                HorizontalDivider()
                Spacer(Modifier.height(8.dp))

                val scrollState = rememberScrollState()

                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    DrawerItem("Dashboard", R.drawable.home_24px, currentRoute, Screen.Dashboard.route, navController, drawerState, scope)
                    Text("Users", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 16.dp, bottom = 4.dp))
                    DrawerItem("User Profile", R.drawable.person_24px, currentRoute, Screen.UserProfile.route, navController, drawerState, scope)
                    DrawerItem("User Management", R.drawable.manage_accounts_24px, currentRoute, Screen.UserManagement.route, navController, drawerState, scope)
                    DrawerItem("Attendance", R.drawable.check_box_24px, currentRoute, Screen.Attendance.route, navController, drawerState, scope)
                    Text("Data", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 16.dp, bottom = 4.dp))
                    DrawerItem("Warehouse", R.drawable.warehouse_24px, currentRoute, Screen.Warehouse.route, navController, drawerState, scope)
                    DrawerItem("Schedule", R.drawable.event_24px, currentRoute, Screen.Schedule.route, navController, drawerState, scope)
                    DrawerItem("Spare Parts", R.drawable.shelves_24px, currentRoute, Screen.SpareParts.route, navController, drawerState, scope)
                    Text("Reports", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 16.dp, bottom = 4.dp))
                    DrawerItem("Repair Reports", R.drawable.construction_24px, currentRoute, Screen.RepairReports.route, navController, drawerState, scope)
                    DrawerItem("Damage Reports", R.drawable.destruction_24px, currentRoute, Screen.DamageReports.route, navController, drawerState, scope)
                    DrawerItem("Attendance Reports", R.drawable.fact_check_24px, currentRoute, Screen.AttendanceReports.route, navController, drawerState, scope)
                    Text("System", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(start = 16.dp, bottom = 4.dp))
                    DrawerItem("Settings", R.drawable.settings_24px, currentRoute, Screen.Settings.route, navController, drawerState, scope)
                    DrawerItem("About", R.drawable.info_24px, currentRoute, Screen.About.route, navController, drawerState, scope)
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("My Awesome App") },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.LightGray
                        )
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Dashboard.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Dashboard.route) { DashboardScreen() }
                    composable(Screen.UserProfile.route) { UserProfileScreen() }
                    composable(Screen.UserManagement.route) { Greeting("User Management") }
                    composable(Screen.Attendance.route) { Greeting("Attendance") }
                    composable(Screen.Warehouse.route) { Greeting("Warehouse") }
                    composable(Screen.Schedule.route) { Greeting("Schedule") }
                    composable(Screen.SpareParts.route) { Greeting("Spare Parts") }
                    composable(Screen.RepairReports.route) { Greeting("Repair Reports") }
                    composable(Screen.DamageReports.route) { Greeting("Damage Reports") }
                    composable(Screen.AttendanceReports.route) { Greeting("Attendance Reports") }
                    composable(Screen.Settings.route) { Greeting("Settings") }
                    composable(Screen.About.route) { Greeting("About") }
                }
            }
        }
    )
}

@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Overview Tiles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OverviewTile(title = "Users", count = 150, iconId = R.drawable.person_24px)
            OverviewTile(title = "Schedules", count = 35, iconId = R.drawable.event_24px)
            OverviewTile(title = "Spare Parts", count = 280, iconId = R.drawable.shelves_24px)
            // Add more overview tiles as needed
        }

        // Rest of the Dashboard Content
        Greeting("Dashboard", modifier = Modifier.weight(1f)) // Example content
    }
}

@Composable
fun OverviewTile(title: String, count: Int, iconId: Int) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(id = iconId),
                contentDescription = title,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text("$count Items", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun UserProfileScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "User Profile Picture",
                            modifier = Modifier.size(120.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("User ID:", fontSize = 18.sp, modifier = Modifier.width(100.dp))
                            Text("12345", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Username:", fontSize = 18.sp, modifier = Modifier.width(100.dp))
                            Text("johndoe", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Password:", fontSize = 18.sp, modifier = Modifier.width(100.dp))
                            Text("abcdef", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Email:", fontSize = 18.sp, modifier = Modifier.width(100.dp))
                            Text("john.doe@example.com", fontSize = 18.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Position:", fontSize = 18.sp, modifier = Modifier.width(100.dp))
                            Text("admin", fontSize = 18.sp)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* TODO: Implement edit functionality */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit Profile", modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit Profile")
            }
        }
    }
}

@Composable
fun DrawerItem(
    label: String,
    iconId: Int,
    currentRoute: String?,
    targetRoute: String,
    navController: NavHostController,
    drawerState: DrawerState,
    scope: kotlinx.coroutines.CoroutineScope
) {
    NavigationDrawerItem(
        label = { Text(text = label) },
        icon = { Icon(painterResource(id = iconId), contentDescription = label) },
        selected = currentRoute == targetRoute,
        onClick = {
            scope.launch { drawerState.close() }
            navController.navigate(targetRoute) {
                launchSingleTop = true
                popUpTo(navController.graph.startDestinationId) { saveState = true }
            }
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "$name Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Proyek1Theme {
        MainScreen()
    }
}