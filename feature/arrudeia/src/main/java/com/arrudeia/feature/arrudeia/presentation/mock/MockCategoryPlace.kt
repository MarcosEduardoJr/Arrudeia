package com.arrudeia.feature.arrudeia.presentation.mock

import com.arrudeia.core.designsystem.R.drawable.ic_building
import com.arrudeia.core.designsystem.R.drawable.ic_car
import com.arrudeia.core.designsystem.R.drawable.ic_hotel
import com.arrudeia.core.designsystem.R.drawable.ic_outdoors
import com.arrudeia.core.designsystem.R.drawable.ic_restaurant
import com.arrudeia.core.designsystem.R.drawable.ic_shop
import com.arrudeia.core.designsystem.R.drawable.ic_surf_person
import com.arrudeia.core.designsystem.R.drawable.ic_train
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaAvailablePlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaCategoryPlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.model.ArrudeiaSubCategoryPlaceUiModel
import com.arrudeia.feature.arrudeia.presentation.ui.AvailableOptions
import com.arrudeia.feature.arrudeia.presentation.ui.CategoryOptions
import com.arrudeia.feature.arrudeia.presentation.ui.SubCategoryOptions

@Suppress("LongMethod")
fun categoriesPlace() = listOf(
    ArrudeiaCategoryPlaceUiModel(
        category = CategoryOptions.CATEGORY_FOOD,
        icon = ic_restaurant,
        subcategories = listOf(
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_RESTAURANT),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_BAR),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_BAKERY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_DESSERT),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_COFFEE_SHOP),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_FAST_FOOD),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_FOOD_COURT),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_WINERY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_ICE_CREAM)

        ),
        available = listOf(
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_AIR_CONDITIONING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_ACCEPTS_CREDIT_CARD),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_PIX),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_MONEY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CURBSIDE_PICKUP),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DELIVERY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DRIVE_THRU),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_OUTDOOR_SEATING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CUSTOMER_PARKING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_RESERVATIONS),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_TAKE_OUT),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_VALET_SERVICE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WHEELCHAIR_ACCESSIBLE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI)
        )
    ), ArrudeiaCategoryPlaceUiModel(
        category = CategoryOptions.CATEGORY_ACCOMMODATION,
        icon = ic_hotel,
        subcategories = listOf(
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_HOTEL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_HOSTEL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CAMPGROUND),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_COTTAGE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_BED_AND_BREAKFAST),
        ),
        available = listOf(
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_AIR_CONDITIONING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_ACCEPTS_CREDIT_CARD),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_PIX),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_MONEY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CURBSIDE_PICKUP),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DELIVERY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DRIVE_THRU),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_OUTDOOR_SEATING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CUSTOMER_PARKING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_RESERVATIONS),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_TAKE_OUT),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_VALET_SERVICE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WHEELCHAIR_ACCESSIBLE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI)
        )
    ), ArrudeiaCategoryPlaceUiModel(
        category = CategoryOptions.CATEGORY_OUTDOORS,
        icon = ic_outdoors,
        subcategories = listOf(
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PARK),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PLAYGROUND),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_BEACH),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SPORT_COURT),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_GOLF_COURSE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PLAZA),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PROMENADE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_POOL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SCENIC_OVERLOOK),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SKI_AREA),
        ),
        available = listOf(
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_AIR_CONDITIONING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_ACCEPTS_CREDIT_CARD),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_PIX),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_MONEY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CURBSIDE_PICKUP),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DELIVERY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DRIVE_THRU),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_OUTDOOR_SEATING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CUSTOMER_PARKING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_RESERVATIONS),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_TAKE_OUT),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_VALET_SERVICE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WHEELCHAIR_ACCESSIBLE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI)
        )
    ), ArrudeiaCategoryPlaceUiModel(
        category = CategoryOptions.CATEGORY_ENTERTAINMENT,
        icon = ic_surf_person,
        subcategories = listOf(
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_ART_GALLERY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CASINO),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CLUB),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_TOURIST_ATTRACTION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_MOVIE_THEATER),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_MUSEUM),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_MUSIC_VENUE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PERFORMANCE_ARTS_VENUE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_GAME_CLUB),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_STADIUM),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_THEME_PARK),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_ZOO_AQUARIUM),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_RACE_TRACK),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_THEATER),
        ),
        available = listOf(
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_AIR_CONDITIONING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_ACCEPTS_CREDIT_CARD),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_PIX),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_MONEY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CURBSIDE_PICKUP),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DELIVERY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DRIVE_THRU),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_OUTDOOR_SEATING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CUSTOMER_PARKING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_RESERVATIONS),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_TAKE_OUT),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_VALET_SERVICE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WHEELCHAIR_ACCESSIBLE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI)
        )
    ), ArrudeiaCategoryPlaceUiModel(
        category = CategoryOptions.CATEGORY_PURCHASES_OR_SERVICES,
        icon = ic_shop,
        subcategories = listOf(
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_ARTS_CRAFTS),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_BANK_FINANCIAL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SPORTING_GOODS),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_BOOKSTORE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PHOTOGRAPHY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CAR_DEALERSHIP),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CLOTHING_FASHION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CONVENIENCE_STORE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PERSONAL_CARE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_DEPARTMENT_STORE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PHARMACY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_ELECTRONICS),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_FLORIST),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_HOME_FURNISHING),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_GIFT_SHOP),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_GYM_FITNESS),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SWIMMING_POOL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_HARDWARE_STORE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_MARKET),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SUPERMARKET_GROCERY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_JEWELRY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_LAUNDROMAT_DRY_CLEANER),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SHOPPING_CENTER),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_MUSIC_STORE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PET_STORE_VETERINARIAN),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_TOY_STORE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_TRAVEL_AGENCY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_ATM),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CURRENCY_EXCHANGE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CAR_RENTAL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CELL_PHONES),
        ),
        available = listOf(
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_AIR_CONDITIONING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_ACCEPTS_CREDIT_CARD),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_PIX),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_MONEY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CURBSIDE_PICKUP),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DELIVERY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DRIVE_THRU),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_OUTDOOR_SEATING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CUSTOMER_PARKING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_RESERVATIONS),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_TAKE_OUT),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_VALET_SERVICE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WHEELCHAIR_ACCESSIBLE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI)
        )
    ), ArrudeiaCategoryPlaceUiModel(
        category = CategoryOptions.CATEGORY_PUBLIC_SERVICE,
        icon = ic_building,
        subcategories = listOf(
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_COLLEGE_UNIVERSITY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SCHOOL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CONVENTIONS_EVENT_CENTER),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_GOVERNMENT),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_LIBRARY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CITY_HALL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_ORGANIZATION_ASSOCIATION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_JAIL_PRISON),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_COURTHOUSE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CEMETERY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_FIRE_DEPARTMENT),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_POLICE_STATION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_MILITARY),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_HOSPITAL_URGENT_CARE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_DOCTOR_CLINIC),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_OFFICES),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_POST_OFFICE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_RELIGIOUS_CENTER),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PRESCHOOL_DAYCARE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_FACTORY_INDUSTRIAL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_EMBASSY_CONSULATE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_INFORMATION_POINT),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_EMERGENCY_SHELTER),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_LANDFILL_RECYCLING_FACILITY),
        ),
        available = listOf(
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_AIR_CONDITIONING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_ACCEPTS_CREDIT_CARD),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_PIX),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_MONEY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CURBSIDE_PICKUP),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DELIVERY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DRIVE_THRU),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_OUTDOOR_SEATING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CUSTOMER_PARKING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_RESERVATIONS),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_TAKE_OUT),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_VALET_SERVICE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WHEELCHAIR_ACCESSIBLE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI)
        )
    ), ArrudeiaCategoryPlaceUiModel(
        category = CategoryOptions.CATEGORY_TRANSPORT,
        icon = ic_train,
        subcategories = listOf(
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_AIRPORT),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_BUS_STATION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_FERRY_PIER),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SEAPORT_MARINA_HARBOR),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_SUBWAY_STATION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_TRAIN_STATION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_BRIDGE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_TUNNEL),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_TAXI_STATION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_JUNCTION_INTERCHANGE),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_REST_AREA),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CARPOOL_PICKUP_DROP_OFF),
        ),
        available = listOf(
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_AIR_CONDITIONING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_ACCEPTS_CREDIT_CARD),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_PIX),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_MONEY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CURBSIDE_PICKUP),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DELIVERY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DRIVE_THRU),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_OUTDOOR_SEATING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CUSTOMER_PARKING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_RESERVATIONS),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_TAKE_OUT),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_VALET_SERVICE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WHEELCHAIR_ACCESSIBLE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI)
        )
    ), ArrudeiaCategoryPlaceUiModel(
        category = CategoryOptions.CATEGORY_CAR_SERVICES,
        icon = ic_car,
        subcategories = listOf(
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_GAS_STATION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_ELETRIC_POWER_STATION),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_WORKSHOP),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_PARKING),
            ArrudeiaSubCategoryPlaceUiModel(SubCategoryOptions.SUBCATEGORY_CAR_WASH),
        ),
        available = listOf(
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_AIR_CONDITIONING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_ACCEPTS_CREDIT_CARD),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_PIX),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_MONEY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CURBSIDE_PICKUP),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DELIVERY),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_DRIVE_THRU),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_OUTDOOR_SEATING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_CUSTOMER_PARKING),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_RESERVATIONS),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_TAKE_OUT),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_VALET_SERVICE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WHEELCHAIR_ACCESSIBLE),
            ArrudeiaAvailablePlaceUiModel(AvailableOptions.AVAILABLE_WI_FI)
        )
    )
)