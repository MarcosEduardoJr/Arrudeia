package com.arrudeia.feature.home.data.entity.hotel

data class HotelDetailResponse(
    val address: String,
    val check_in_time: String,
    val check_out_time: String,
    val description: String,
    val eco_certified: Boolean,
    val extracted_hotel_class: Int,
    val featured_prices: List<FeaturedPrice>? = listOf(),
    val gps_coordinates: HotelDetailGpsCoordinates,
    val hotel_class: String,
    val images: List<Image>,
    val link: String,
    val location_rating: Double,
    val name: String,
    val nearby_places: List<HotelDetailNearbyPlace>,
    val overall_rating: Double,
    val phone: String,
    val phone_link: String,
    val prices: List<HotelDetailPrice>,
    val rate_per_night: HotelDetailRatePerNight,
    val ratings: List<Rating>,
    val reviews: Int,
    val reviews_breakdown: List<HotelDetailReviewsBreakdown>,
    val search_metadata: HotelDetailSearchMetadata,
    val search_parameters: HotelDetailSearchParameters,
    val total_rate: HotelDetailTotalRate,
    val type: String,
    val typical_price_range: TypicalPriceRange,
    val amenities: List<String>,
)
/*

{
    "search_metadata": {
    "id": "66de5247690bb990198bbf43",
    "status": "Success",
    "json_endpoint": "https://serpapi.com/searches/8daf391cd5af538b/66de5247690bb990198bbf43.json",
    "created_at": "2024-09-09 01:41:27 UTC",
    "processed_at": "2024-09-09 01:41:27 UTC",
    "google_hotels_url": "https://www.google.com/_/TravelFrontendUi/data/batchexecute?rpcids=AtySUc&source-path=/travel/search&hl=pt-br&gl=br&rt=c&soc-app=162&soc-platform=1&soc-device=1",
    "raw_html_file": "https://serpapi.com/searches/8daf391cd5af538b/66de5247690bb990198bbf43.html",
    "prettify_html_file": "https://serpapi.com/searches/8daf391cd5af538b/66de5247690bb990198bbf43.prettify",
    "total_time_taken": 1.48
},
    "search_parameters": {
    "engine": "google_hotels",
    "q": "Santa Clara County",
    "gl": "br",
    "hl": "pt-br",
    "currency": "BRL",
    "check_in_date": "2024-09-09",
    "check_out_date": "2024-09-10",
    "adults": 1,
    "children": 0,
    "property_token": "ChkI6oWGtvapqtl1Gg0vZy8xMWYzMmM3amY1EAE"
},
    "type": "hotel",
    "name": "Quality Inn & Suites South San Jose / Morgan Hill",
    "link": "https://www.choicehotels.com/california/morgan-hill/quality-inn-hotels/ca991?mc=llgoxxpx",
    "address": "16525 Condit Rd, Morgan Hill, CA 95037",
    "phone": "(408) 779-0447",
    "phone_link": "tel:+14087790447",
    "gps_coordinates": {
    "latitude": 37.1264405,
    "longitude": -121.62856729999999
},
    "check_in_time": "15:00",
    "check_out_time": "11:00",
    "rate_per_night": {
    "lowest": "R$ 409",
    "extracted_lowest": 409,
    "before_taxes_fees": "R$ 347",
    "extracted_before_taxes_fees": 347
},
    "total_rate": {
    "lowest": "R$ 409",
    "extracted_lowest": 409,
    "before_taxes_fees": "R$ 347",
    "extracted_before_taxes_fees": 347
},
    "featured_prices": [
    {
        "source": "Booking.com",
        "link": "https://www.google.com/aclk?sa=l&ai=Ca7IJSFLeZs_hHsu-tOUP0tqz0A-O17fPeLXbiq_SEsyy3LD3QwgKEAEg6ZDtigEoBGDJhoCAyKOgGaAB0p2k8D3IAQipAkoKLIApm7I-qgRWT9AYH4VobLxwEtBxQD-GIdiBVnZYNG7uqRODi7pcki8OUlLr8n_TBoXnT89fm7wNwY_TXTvilRpsSl8JZVe-hBurx2r7xIO6ISnBdrdLTTfqmPz7y2_ABLb09c3dBIgFoInA3k_ABZIBoAZlkAcDqAfruLECqAemvhuoB7masQKoB_PRG6gH7tIbqAf_nLECqAfK3BuoB5KvsQKoB7uksQKoB9imsQKoB961sQKoB9uqsQKoB9CqsQKoB9ywsQKoB-qxsQKoB5S4sQKoB-y4sQKoB763sQKoB9vFsQKoB4PDsQKgCJgWsAgBwAgB0ggmEAEyAoBAOgvgYYLAgICAoICAAkIBAUjeuNYyUAlYz6Wr2N20iAPICawB-AkBogr6CQoKMjAyNC0wOS0wORABGgJVUyIFcHQtQlIpcEtm3Sxd1ooyE2Jvb2tpbmcuY29tU3RhbmRhcmQ4AkgBUhg0ODkxNDMwOF8zNTIwMTIwNDFfMl8xXzBdZmaXQmVwPUdBaAByA1VTRIIBpQEKCDQ4OTE0MzA4GjMKAkM1Ei1lZGd0aWQ9dFNkYU45VlRSR3VBb0VqTTV1QUZUQSZlZnBjPWRiY0tpTEZhTHcaNgoCQzESMHVzL3F1YWxpdHktaW5uLXN1aXRlcy1zb3V0aC1zYW4tam9zZS1tb3JnYW4taGlsbBoICgJDMhICMTAaHgoCQzMSGG5vX3Jvb21zPTEmdHM9MTcyNTg0MzA3MTICCAGKAQZwdWJsaWOgAQGwAQG4AQHIAf_N5C_gAQLoAQHwAQH4AQCoAswBqALlA7AC0p2k8D24AqCJwN5PygIiCO3xjtgYELb09c3dBBiS8j0gmBYozLLcsPdDOJ7wlLq5BOACAOoCA0JSTPoC_AZodHRwczovL3d3dy5ib29raW5nLmNvbS9ob3RlbC91cy9xdWFsaXR5LWlubi1zdWl0ZXMtc291dGgtc2FuLWpvc2UtbW9yZ2FuLWhpbGwuaHRtbD9jaGVja2luPTIwMjQtMDktMDkmY2hlY2tvdXQ9MjAyNC0wOS0xMCZncm91cF9hZHVsdHM9MSZyZXFfYWR1bHRzPTEmc2hvd19yb29tPTQ4OTE0MzA4XzM1MjAxMjA0MV8yXzFfMCZsYW5nPXB0JnNlbGVjdGVkX2N1cnJlbmN5PUJSTCZleHJ0PTUuNTk4MTAwMTkmZXh0X3ByaWNlX3RvdGFsPTQ5My40OSZleHRfcHJpY2VfdGF4PTY5LjcxJnhmYz1VU0QmaGNhPW0mZ3JvdXBfY2hpbGRyZW49MCZyZXFfY2hpbGRyZW49MCYmbm9fcm9vbXM9MSZ0cz0xNzI1ODQzMDcxJmVkZ3RpZD10U2RhTjlWVFJHdUFvRWpNNXVBRlRBJmVmcGM9ZGJjS2lMRmFMdyZ1dG1fc291cmNlPW1ldGFnaGEmdXRtX21lZGl1bT1sb2NhbHVuaXZlcnNhbCZ1dG1fY2FtcGFpZ249VVMmdXRtX3Rlcm09aG90ZWwtNDg5MTQzJnV0bV9jb250ZW50PWRldi1kZXNrdG9wX2xvcy0xX2J3LTFfZG93LU1vbmRheV9kZWZkYXRlLTBfcm9vbS0wX2dzdGFkdC0xX3JhdGVpZC1wdWJsaWNfYXVkLTBfZ2FjaWQtMjE0MDQ1ODMwNzJfbWNpZC0xMF9wcGEtMF9jbHJpZC0wX2FkLTFfZ3N0a2lkLTBfY2hlY2tpbi0yMDI0MDkwOV9wcHQtJmFpZD0zNTY5MjkmbGFiZWw9bWV0YWdoYS1saW5rLUxVVVMtaG90ZWwtNDg5MTQzX2Rldi1kZXNrdG9wX2xvcy0xX2J3LTFfZG93LU1vbmRheV9kZWZkYXRlLTBfcm9vbS0wX2dzdGFkdC0xX3JhdGVpZC1wdWJsaWNfYXVkLTBfZ2FjaWQtMjE0MDQ1ODMwNzJfbWNpZC0xMF9wcGEtMF9jbHJpZC0wX2FkLTFfZ3N0a2lkLTBfY2hlY2tpbi0yMDI0MDkwOV9wcHQtX2xwLTI4NDBfci0xMzkwODgzODY4MTIyNTY3MTg4MSZnY2xpZD17Z2NsaWR9igMA6AoBkAsDmAsB0Asa6AwTmg0BGqoNAlVTyA0BghQECHoSANAVAfgWAYAXAZIXCRIHCAEQAxjAAroXBDgBSAHQGAGIGQE&co=1&gclid=EAIaIQobChMIz6Wr2N20iAMVSx-tBh1S7Qz6EAoYASABEgIFEvD_BwE&sig=AOD64_31f3AuPZEHA2H9zFFBd3xFW1R02A&adurl=https://www.booking.com/hotel/us/quality-inn-suites-south-san-jose-morgan-hill.html?checkin%3D2024-09-09%26checkout%3D2024-09-10%26group_adults%3D1%26req_adults%3D1%26show_room%3D48914308_352012041_2_1_0%26lang%3Dpt%26selected_currency%3DBRL%26exrt%3D5.59810019%26ext_price_total%3D493.49%26ext_price_tax%3D69.71%26xfc%3DUSD%26hca%3Dm%26group_children%3D0%26req_children%3D0%26%26no_rooms%3D1%26ts%3D1725843071%26edgtid%3DtSdaN9VTRGuAoEjM5uAFTA%26efpc%3DdbcKiLFaLw%26utm_source%3Dmetagha%26utm_medium%3Dlocaluniversal%26utm_campaign%3DUS%26utm_term%3Dhotel-489143%26utm_content%3Ddev-desktop_los-1_bw-1_dow-Monday_defdate-0_room-0_gstadt-1_rateid-public_aud-0_gacid-21404583072_mcid-10_ppa-0_clrid-0_ad-1_gstkid-0_checkin-20240909_ppt-%26aid%3D356929%26label%3Dmetagha-link-LUUS-hotel-489143_dev-desktop_los-1_bw-1_dow-Monday_defdate-0_room-0_gstadt-1_rateid-public_aud-0_gacid-21404583072_mcid-10_ppa-0_clrid-0_ad-1_gstkid-0_checkin-20240909_ppt-_lp-2840_r-2282501918894494362%26gclid%3D%7Bgclid%7D%26gad_source%3D0",
        "logo": "https://www.gstatic.com/travel-hotels/branding/icon_184.png",
        "remarks": [
        "Sem custos ocultos",
        "Apoio ao cliente 24 horas"
        ],
        "rooms": [
        {
            "name": "Quarto Standard com Cama King-size - Não Fumantes",
            "images": [
            "https://lh3.googleusercontent.com/hrppk/ANjXD_yYnWDxEb8uFWS5zGRBIrsiK1gcJDeeERGIhLMnLAWF7aTrY_Z8wnWD1BtvrlyPsLKPKCcKWUIW67_4jIkAUqlErtLf_JgaWjyiDk4ZqrtKztxxF3G3QuLCMttXbk3HLPoALx7PkzOg8k8EFgSmYL3vbUTvefEVLAW_Bjda0eU2OGXlnZ7QJLYqLJtamLynET59mBCHSRKrJ6qIT696C89se8hsL6vJbo8",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_wLiZSbcjtLPJ1RYTkgaIunbJQdVtH179QI8Lcr83Gt-oXjpn5w2dP1p2JdXwHicNKHyD3YV51ytfVbgAVajt0gxXYaSROqeLrrv2EiY9B0zwgxFauHru9KgIlsGUuD80KNFlvrcM0yIRuOA0IX-a7k5MigLkv5j8Boo4VNqLjm9IT7w3mscpE435el4hTAvZY26jr8LtkopSa0MfDgLFkyl6NNFWwhdg",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_zGVLEXdXQio4sa37QKXlRHjJitMm8lB6n0Mfcg_BxEqK0Pt0_yV8I_jTJQfXKjgci1Q5N4o7tTGVcA_EQU3vScySSWxAwh_6j9TM0SGXigbjzvjyBOIAKQpn0wu-864t3qm3qqoRwsLi8qgTr-BcBMMnLnQfqvPc9nBUZ4rxQDWxhnRF6IwBnBp1wMTYuXsTkDFo8FctVVXu72Ky6MX5YrElCBHXlgen4",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_wD_GNdrvtG0scIbA_tT_mmJ80nWClKvrFIcMJvCYRE_hbax2RdKNE9CVQC_flAROy6xpwlb8glRu381PoXSy-hWc9arorwiHI2BnsHGI5GyickkC_BpGi95_--F-Ly-_W2UXbyPi7MEULBoWOEQsm26kdyGrOgyK7JUpOv6GyUvaptq-zxmfKMWcBC7EjiNtFWWgz9OOx0CY6t0-9R4xUN4jiUTdLc-A",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_w6Y_bVPCAQylLyCNdLPaW0gbCFoEdd0zriNYzkzbji9TiDpGeMl4kEK2nzkU3c7Bg5HcMvp9eKjxd4ZsbVBcGbCLqpGlY9Kr_q8h1UYNi3eaFmSpIydieMyAHNS45q7FEuh-iU8Sd5mkV-bGqfp8I1XU1iM7rnkpxhwJOmvBB4bl56MO9r6qUsJH8B5oNIl04Hzz1aBC4pRaGbuNjmNLI6PPgaL4w_H04",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_wVRnTudJ4znbGCqTo1uDMKlC-_ZlTQ2Ucda6dO6VPujnqLqQVzGd7HejI2rA4QnTxShxQFYoAU2Ktp1vl8Xvm4RZ0vLe_W2f8D4hhi8TELMAw9XdbsLv4wDyaPW07PiT21wrGTIdCaT1orAF8hyJAle80EemzJkoZUGPQrCivZPc_qnKZnX-xisPvt5FOTlJ4g0mgR0HCLLkyER467ZGZAzRYxpaOoNA",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_yLYgymFPDhM8OaJNVLOZrVsP_dnqPgZiRQAZKRV5Ojk0NAbPmCu_Cw-HyfEz0UYEjbgs5YJSEe1m7eSOQ1hSwmiwviA6hNtBr0zXb-RK3T8C5GJwJPZmgTDqlmLyoUHDQfIjluJn9ZiftmCCF9S6aKb9pwWOv5ILQZyZu75pDH56szuCmah4Rcw4EdQd22jnD5kJXgv3pAgHwqyhFR8BvD-0LNjC4UnEw"
            ],
            "link": "https://www.google.com/aclk?sa=l&ai=CRTixSFLeZs_hHsu-tOUP0tqz0A-O17fPeLXbiq_SEsyy3LD3QwgKEAEg6ZDtigEoBGDJhoCAyKOgGaAB0p2k8D3IAQipAkoKLIApm7I-qgRWT9AYH4VobLxwEtBxQD-GIdiBVnZYNG7uqRODi7pcki8OUlLr8n_TBoXnT89fm7wNwY_TXTvilRpsSl8JZVe-hBurx2r7xIO6ISnBdrdLTTfqmPz7y2_ABLb09c3dBIgFoInA3k_ABZIBoAZlkAcDqAfruLECqAemvhuoB7masQKoB_PRG6gH7tIbqAf_nLECqAfK3BuoB5KvsQKoB7uksQKoB9imsQKoB961sQKoB9uqsQKoB9CqsQKoB9ywsQKoB-qxsQKoB5S4sQKoB-y4sQKoB763sQKoB9vFsQKoB4PDsQKgCJgWsAgBwAgC0ggmEAEyAoBAOgvgYYLAgICAoICAAkIBAUjeuNYyUAlYz6Wr2N20iAPICawB-AkBogrKCQoKMjAyNC0wOS0wORABGgJVUyIFcHQtQlIpcEtm3Sxd1ooyE2Jvb2tpbmcuY29tU3RhbmRhcmQ4AkgBUgBdWePTQ2Wya4tCaAByA0JSTIIBpQEKCDQ4OTE0MzA4GjMKAkM1Ei1lZGd0aWQ9dFNkYU45VlRSR3VBb0VqTTV1QUZUQSZlZnBjPWRiY0tpTEZhTHcaNgoCQzESMHVzL3F1YWxpdHktaW5uLXN1aXRlcy1zb3V0aC1zYW4tam9zZS1tb3JnYW4taGlsbBoICgJDMhICMTAaHgoCQzMSGG5vX3Jvb21zPTEmdHM9MTcyNTg0MzA3MTICCAKKAQZwdWJsaWOgAQGwAQK4AQHIAf_N5C_gAQLoAQHwAQH4AQCAAgGoAswBqALlA7AC0p2k8D24AqCJwN5PygIiCO3xjtgYELb09c3dBBiS8j0gmBYozLLcsPdDOJ7wlLq5BOACAOoCA0JSTPoC4QZodHRwczovL3d3dy5ib29raW5nLmNvbS9ob3RlbC91cy9xdWFsaXR5LWlubi1zdWl0ZXMtc291dGgtc2FuLWpvc2UtbW9yZ2FuLWhpbGwuaHRtbD9jaGVja2luPTIwMjQtMDktMDkmY2hlY2tvdXQ9MjAyNC0wOS0xMCZncm91cF9hZHVsdHM9MiZyZXFfYWR1bHRzPTImc2hvd19yb29tPSZsYW5nPXB0JnNlbGVjdGVkX2N1cnJlbmN5PUJSTCZleHJ0PTEuMDAwMDAwMDAmZXh0X3ByaWNlX3RvdGFsPTQ5My40OSZleHRfcHJpY2VfdGF4PTY5LjcxJnhmYz1CUkwmaGNhPW0mZ3JvdXBfY2hpbGRyZW49MCZyZXFfY2hpbGRyZW49MCYmbm9fcm9vbXM9MSZ0cz0xNzI1ODQzMDcxJmVkZ3RpZD10U2RhTjlWVFJHdUFvRWpNNXVBRlRBJmVmcGM9ZGJjS2lMRmFMdyZ1dG1fc291cmNlPW1ldGFnaGEmdXRtX21lZGl1bT1sb2NhbHVuaXZlcnNhbCZ1dG1fY2FtcGFpZ249VVMmdXRtX3Rlcm09aG90ZWwtNDg5MTQzJnV0bV9jb250ZW50PWRldi1kZXNrdG9wX2xvcy0xX2J3LTFfZG93LU1vbmRheV9kZWZkYXRlLTBfcm9vbS1fZ3N0YWR0LTJfcmF0ZWlkLXB1YmxpY19hdWQtMF9nYWNpZC0yMTQwNDU4MzA3Ml9tY2lkLTEwX3BwYS0wX2NscmlkLTBfYWQtMV9nc3RraWQtMF9jaGVja2luLTIwMjQwOTA5X3BwdC0mYWlkPTM1NjkyOSZsYWJlbD1tZXRhZ2hhLWxpbmstTFVVUy1ob3RlbC00ODkxNDNfZGV2LWRlc2t0b3BfbG9zLTFfYnctMV9kb3ctTW9uZGF5X2RlZmRhdGUtMF9yb29tLV9nc3RhZHQtMl9yYXRlaWQtcHVibGljX2F1ZC0wX2dhY2lkLTIxNDA0NTgzMDcyX21jaWQtMTBfcHBhLTBfY2xyaWQtMF9hZC0xX2dzdGtpZC0wX2NoZWNraW4tMjAyNDA5MDlfcHB0LV9scC0yODQwX3ItMTIwNDE2MTk0MzQ2Mzg4NjY3MyZnY2xpZD17Z2NsaWR9igMA6AoBkAsDmAsB0Asa6AwTmg0BGqoNAlVTyA0BghQECHoSANAVAfgWAYAXAZIXCRIHCAEQAxjAAroXBDgBSAHQGAGIGQE&co=1&gclid=EAIaIQobChMIz6Wr2N20iAMVSx-tBh1S7Qz6EAoYASACEgJbRvD_BwE&sig=AOD64_2HknZPHZQXkw61VtYFkjmHaRMERw&adurl=https://www.booking.com/hotel/us/quality-inn-suites-south-san-jose-morgan-hill.html?checkin%3D2024-09-09%26checkout%3D2024-09-10%26group_adults%3D2%26req_adults%3D2%26show_room%3D%26lang%3Dpt%26selected_currency%3DBRL%26exrt%3D1.00000000%26ext_price_total%3D493.49%26ext_price_tax%3D69.71%26xfc%3DBRL%26hca%3Dm%26group_children%3D0%26req_children%3D0%26%26no_rooms%3D1%26ts%3D1725843071%26edgtid%3DtSdaN9VTRGuAoEjM5uAFTA%26efpc%3DdbcKiLFaLw%26utm_source%3Dmetagha%26utm_medium%3Dlocaluniversal%26utm_campaign%3DUS%26utm_term%3Dhotel-489143%26utm_content%3Ddev-desktop_los-1_bw-1_dow-Monday_defdate-0_room-_gstadt-2_rateid-public_aud-0_gacid-21404583072_mcid-10_ppa-0_clrid-0_ad-1_gstkid-0_checkin-20240909_ppt-%26aid%3D356929%26label%3Dmetagha-link-LUUS-hotel-489143_dev-desktop_los-1_bw-1_dow-Monday_defdate-0_room-_gstadt-2_rateid-public_aud-0_gacid-21404583072_mcid-10_ppa-0_clrid-0_ad-1_gstkid-0_checkin-20240909_ppt-_lp-2840_r-2282501918894494362%26gclid%3D%7Bgclid%7D%26gad_source%3D0",
            "num_guests": 2,
            "rate_per_night": {
            "lowest": "R$ 493",
            "extracted_lowest": 493,
            "before_taxes_fees": "R$ 441",
            "extracted_before_taxes_fees": 441
        },
            "total_rate": {
            "lowest": "R$ 493",
            "extracted_lowest": 493,
            "before_taxes_fees": "R$ 441",
            "extracted_before_taxes_fees": 441
        }
        },
        {
            "name": "Quarto Queen Standard com 2 Camas Queen - Não-Fumante",
            "images": [
            "https://lh3.googleusercontent.com/hrppk/ANjXD_zr2QZQaF-AA7LajCVRsOa0KowkX71Vi2grRR-dWeewY4VtJPRX9_9r-2PzttedyyHQ5FZYNyEQd3T_3PglYPQDEIkLQb-TebhANNJ6t1PELCwz4XrflN-FtAbdBIGC9JQV5Uskv6cqG7bkjVM6BVTO7D3ohKqHl3AKuf3s1ZhIm1nNsUc7NJ5saB77pAZDqQ0Kr7QC0gvjxqh35MN39VroIKdUro9imtA",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_wLiZSbcjtLPJ1RYTkgaIunbJQdVtH179QI8Lcr83Gt-oXjpn5w2dP1p2JdXwHicNKHyD3YV51ytfVbgAVajt0gxXYaSROqeLrrv2EiY9B0zwgxFauHru9KgIlsGUuD80KNFlvrcM0yIRuOA0IX-a7k5MigLkv5j8Boo4VNqLjm9IT7w3mscpE435el4hTAvZY26jr8LtkopSa0MfDgLFkyl6NNFWwhdg",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_zGVLEXdXQio4sa37QKXlRHjJitMm8lB6n0Mfcg_BxEqK0Pt0_yV8I_jTJQfXKjgci1Q5N4o7tTGVcA_EQU3vScySSWxAwh_6j9TM0SGXigbjzvjyBOIAKQpn0wu-864t3qm3qqoRwsLi8qgTr-BcBMMnLnQfqvPc9nBUZ4rxQDWxhnRF6IwBnBp1wMTYuXsTkDFo8FctVVXu72Ky6MX5YrElCBHXlgen4",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_wD_GNdrvtG0scIbA_tT_mmJ80nWClKvrFIcMJvCYRE_hbax2RdKNE9CVQC_flAROy6xpwlb8glRu381PoXSy-hWc9arorwiHI2BnsHGI5GyickkC_BpGi95_--F-Ly-_W2UXbyPi7MEULBoWOEQsm26kdyGrOgyK7JUpOv6GyUvaptq-zxmfKMWcBC7EjiNtFWWgz9OOx0CY6t0-9R4xUN4jiUTdLc-A",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_w6Y_bVPCAQylLyCNdLPaW0gbCFoEdd0zriNYzkzbji9TiDpGeMl4kEK2nzkU3c7Bg5HcMvp9eKjxd4ZsbVBcGbCLqpGlY9Kr_q8h1UYNi3eaFmSpIydieMyAHNS45q7FEuh-iU8Sd5mkV-bGqfp8I1XU1iM7rnkpxhwJOmvBB4bl56MO9r6qUsJH8B5oNIl04Hzz1aBC4pRaGbuNjmNLI6PPgaL4w_H04",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_wVRnTudJ4znbGCqTo1uDMKlC-_ZlTQ2Ucda6dO6VPujnqLqQVzGd7HejI2rA4QnTxShxQFYoAU2Ktp1vl8Xvm4RZ0vLe_W2f8D4hhi8TELMAw9XdbsLv4wDyaPW07PiT21wrGTIdCaT1orAF8hyJAle80EemzJkoZUGPQrCivZPc_qnKZnX-xisPvt5FOTlJ4g0mgR0HCLLkyER467ZGZAzRYxpaOoNA",
            "https://lh3.googleusercontent.com/hrppk/ANjXD_yLYgymFPDhM8OaJNVLOZrVsP_dnqPgZiRQAZKRV5Ojk0NAbPmCu_Cw-HyfEz0UYEjbgs5YJSEe1m7eSOQ1hSwmiwviA6hNtBr0zXb-RK3T8C5GJwJPZmgTDqlmLyoUHDQfIjluJn9ZiftmCCF9S6aKb9pwWOv5ILQZyZu75pDH56szuCmah4Rcw4EdQd22jnD5kJXgv3pAgHwqyhFR8BvD-0LNjC4UnEw"
            ],
            "link": "https://www.google.com/aclk?sa=l&ai=CpehkSFLeZs_hHsu-tOUP0tqz0A-O17fPeLXbiq_SEsyy3LD3QwgKEAEg6ZDtigEoBGDJhoCAyKOgGaAB0p2k8D3IAQipAkoKLIApm7I-qgRWT9AYH4VobLxwEtBxQD-GIdiBVnZYNG7uqRODi7pcki8OUlLr8n_TBoXnT89fm7wNwY_TXTvilRpsSl8JZVe-hBurx2r7xIO6ISnBdrdLTTfqmPz7y2_ABLb09c3dBIgFoInA3k_ABZIBoAZlkAcDqAfruLECqAemvhuoB7masQKoB_PRG6gH7tIbqAf_nLECqAfK3BuoB5KvsQKoB7uksQKoB9imsQKoB961sQKoB9uqsQKoB9CqsQKoB9ywsQKoB-qxsQKoB5S4sQKoB-y4sQKoB763sQKoB9vFsQKoB4PDsQKgCJgWsAgBwAgD0ggmEAEyAoBAOgvgYYLAgICAoICAAkIBAUjeuNYyUAlYz6Wr2N20iAPICawB-AkBogrLCQoKMjAyNC0wOS0wORABGgJVUyIFcHQtQlIpcEtm3Sxd1ooyE2Jvb2tpbmcuY29tU3RhbmRhcmQ4AkgBUgBdtb4ERGW6OKZCaAByA0JSTIIBpQEKCDQ4OTE0MzA5GjMKAkM1Ei1lZGd0aWQ9dFNkYU45VlRSR3VBb0VqTTV1QUZUQSZlZnBjPWxMaGFFSnR3b2caNgoCQzESMHVzL3F1YWxpdHktaW5uLXN1aXRlcy1zb3V0aC1zYW4tam9zZS1tb3JnYW4taGlsbBoICgJDMhICMTAaHgoCQzMSGHRzPTE3MjU4NDMwNzEmbm9fcm9vbXM9MTICCASKAQZwdWJsaWOgAQGwAQK4AQHIAf_N5C_gAQLoAQHwAQH4AQCAAgGoAswBqALlA7AC0p2k8D24AqCJwN5PygIiCO3xjtgYELb09c3dBBiS8j0gmBYozLLcsPdDOJ7wlLq5BOACAOoCA0JSTPoC4gZodHRwczovL3d3dy5ib29raW5nLmNvbS9ob3RlbC91cy9xdWFsaXR5LWlubi1zdWl0ZXMtc291dGgtc2FuLWpvc2UtbW9yZ2FuLWhpbGwuaHRtbD9jaGVja2luPTIwMjQtMDktMDkmY2hlY2tvdXQ9MjAyNC0wOS0xMCZncm91cF9hZHVsdHM9NCZyZXFfYWR1bHRzPTQmc2hvd19yb29tPSZsYW5nPXB0JnNlbGVjdGVkX2N1cnJlbmN5PUJSTCZleHJ0PTEuMDAwMDAwMDAmZXh0X3ByaWNlX3RvdGFsPTYxNC4wOSZleHRfcHJpY2VfdGF4PTgzLjExJnhmYz1CUkwmaGNhPW0mZ3JvdXBfY2hpbGRyZW49MCZyZXFfY2hpbGRyZW49MCYmdHM9MTcyNTg0MzA3MSZub19yb29tcz0xJmVkZ3RpZD10U2RhTjlWVFJHdUFvRWpNNXVBRlRBJmVmcGM9bExoYUVKdHdvZyZ1dG1fc291cmNlPW1ldGFnaGEmdXRtX21lZGl1bT1sb2NhbHVuaXZlcnNhbCZ1dG1fY2FtcGFpZ249VVMmdXRtX3Rlcm09aG90ZWwtNDg5MTQzJnV0bV9jb250ZW50PWRldi1kZXNrdG9wX2xvcy0xX2J3LTFfZG93LU1vbmRheV9kZWZkYXRlLTBfcm9vbS1fZ3N0YWR0LTRfcmF0ZWlkLXB1YmxpY19hdWQtMF9nYWNpZC0yMTQwNDU4MzA3Ml9tY2lkLTEwX3BwYS0wX2NscmlkLTBfYWQtMV9nc3RraWQtMF9jaGVja2luLTIwMjQwOTA5X3BwdC0mYWlkPTM1NjkyOSZsYWJlbD1tZXRhZ2hhLWxpbmstTFVVUy1ob3RlbC00ODkxNDNfZGV2LWRlc2t0b3BfbG9zLTFfYnctMV9kb3ctTW9uZGF5X2RlZmRhdGUtMF9yb29tLV9nc3RhZHQtNF9yYXRlaWQtcHVibGljX2F1ZC0wX2dhY2lkLTIxNDA0NTgzMDcyX21jaWQtMTBfcHBhLTBfY2xyaWQtMF9hZC0xX2dzdGtpZC0wX2NoZWNraW4tMjAyNDA5MDlfcHB0LV9scC0yODQwX3ItMTE3NzY3NDUwMTE5NjYwNDc1NTUmZ2NsaWQ9e2djbGlkfYoDAOgKAZALA5gLAdALGugME5oNARqqDQJVU8gNAYIUBAh6EgDQFQH4FgGAFwGSFwkSBwgBEAMYwAK6FwQ4AUgB0BgBiBkB&co=1&gclid=EAIaIQobChMIz6Wr2N20iAMVSx-tBh1S7Qz6EAoYASADEgKAX_D_BwE&sig=AOD64_0WoBnDQKzKyDI8l7gvY0oGzm2URw&adurl=https://www.booking.com/hotel/us/quality-inn-suites-south-san-jose-morgan-hill.html?checkin%3D2024-09-09%26checkout%3D2024-09-10%26group_adults%3D4%26req_adults%3D4%26show_room%3D%26lang%3Dpt%26selected_currency%3DBRL%26exrt%3D1.00000000%26ext_price_total%3D614.09%26ext_price_tax%3D83.11%26xfc%3DBRL%26hca%3Dm%26group_children%3D0%26req_children%3D0%26%26ts%3D1725843071%26no_rooms%3D1%26edgtid%3DtSdaN9VTRGuAoEjM5uAFTA%26efpc%3DlLhaEJtwog%26utm_source%3Dmetagha%26utm_medium%3Dlocaluniversal%26utm_campaign%3DUS%26utm_term%3Dhotel-489143%26utm_content%3Ddev-desktop_los-1_bw-1_dow-Monday_defdate-0_room-_gstadt-4_rateid-public_aud-0_gacid-21404583072_mcid-10_ppa-0_clrid-0_ad-1_gstkid-0_checkin-20240909_ppt-%26aid%3D356929%26label%3Dmetagha-link-LUUS-hotel-489143_dev-desktop_los-1_bw-1_dow-Monday_defdate-0_room-_gstadt-4_rateid-public_aud-0_gacid-21404583072_mcid-10_ppa-0_clrid-0_ad-1_gstkid-0_checkin-20240909_ppt-_lp-2840_r-2282501918894494362%26gclid%3D%7Bgclid%7D%26gad_source%3D0",
            "num_guests": 4,
            "rate_per_night": {
            "lowest": "R$ 614",
            "extracted_lowest": 614,
            "before_taxes_fees": "R$ 548",
            "extracted_before_taxes_fees": 548
        },
            "total_rate": {
            "lowest": "R$ 614",
            "extracted_lowest": 614,
            "before_taxes_fees": "R$ 548",
            "extracted_before_taxes_fees": 548
        }
        }
        ],
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    },
        "total_rate": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    }
    },
    {
        "source": "Quality Inn & Suites South San Jose / Morgan Hill",
        "link": "https://www.google.com/aclk?sa=l&ai=CrvSnSFLeZs_hHsu-tOUP0tqz0A_T--CvdKH5vvuZEpfLvsa1QggKEAIg6ZDtigEoBGDJhoCAyKOgGaABm4Gu-QLIAQiqBFVP0HkbfXR2jUAq6fDCPE_reOhYQtH2tMEIsGjbm56BqujAP4HtpgbDjedvrsZLtpVRpvj7Abrzw02zf3sQ9rQwuoPlavfQPbBaPWF2lUtDIQuIrfwTwATMg8Wj1ASIBZyYzYpNwAWSAaAGZZAHA6gH67ixAqgHpr4bqAe5mrECqAfz0RuoB-7SG6gH_5yxAqgHytwbqAeSr7ECqAe7pLECqAfYprECqAfetbECqAfbqrECqAfQqrECqAfcsLECqAfqsbECqAeUuLECqAfsuLECqAe-t7ECqAfbxbECqAeDw7ECqAe8rbECqAe5q7ECqAfotbECoAiYFrAIAcAIAdIIJhABMgKAQDoL4GGCwICAgKCAgAJCAQFI3rjWMlAJWM-lq9jdtIgDyAmsAfgJAaIKgwUKCjIwMjQtMDktMDkQARoCVVMiBXB0LUJSKXBLZt0sXdaKMgxjaG9pY2Vob3RlbHM4AkgBUgRSQUNLXc3MjEJlzcwMQWgAcgNVU0SCARoKAk5LGgoKAkMxEgRTQ1BNMgIIAUIEUkFDS4oBEE1lbWJlcl9SYXRlX1J1bGWgAQGwAQG4AQHIAbuF2i_gAQHoAQLwAQH4AQGoAswBqALlA7ACm4Gu-QK4ApyYzYpN4AIA6gIDQlJM-gLHA2h0dHBzOi8vdHJhdmVsLXByb2R1Y3RhZHMua29kZGkuY29tL1Byb3BlcnR5QWR2b2NhdGVBUEkvQ2xpY2tSZWRpcmVjdD9jbGllbnQ9Q2hvaWNlJmNoYW5uZWw9R0hQQSZwbGFjZW1lbnQ9bG9jYWx1bml2ZXJzYWwmY2FtcGFpZ249MjA2OTE3NjYzMDAmaG90ZWw9Q0E5OTEmZGVzdGluYXRpb25Vcmw9aHR0cHM6Ly93d3cuY2hvaWNlaG90ZWxzLmNvbS9DQTk5MT9jaGVja0luRGF0ZT0yMDI0LTA5LTA5JmNoZWNrT3V0RGF0ZT0yMDI0LTA5LTEwJnNycD1TQ1BNJmFkdWx0cz0xJm1pbm9ycz0wJm1jPUhBR09IUFVTJm1ldGE9UE1GR1BBRFVTU0NQTV9DQTk5MV9sb2NhbHVuaXZlcnNhbF9VU18xX2Rlc2t0b3BfMjAyNC0wOS0wOV9zZWxlY3RlZF8yMDY5MTc2NjMwMF9fcGFpZCZnYWw9JnBtZj1ocGFnb29nbGUmZ3BhPUdQQURTQ1BNJnJvb21zPTEmZ21wPU1ldGFBZCZwcm9kdWN0PWxvY2FsdW5pdmVyc2FsigMA6AoCkAsDmAsB0Asa6AwTmg0BGqoNAlVTyA0BghQECHoSANAVAZgWAfgWAYAXAZIXCRIHCAEQAxjAAroXBDgBSAHQGAGIGQE&co=1&gclid=EAIaIQobChMIz6Wr2N20iAMVSx-tBh1S7Qz6EAoYAiABEgIth_D_BwE&sig=AOD64_0Dgxw0emwX058BP4X9K0tJK7ezlg&adurl=https://travel-productads.koddi.com/PropertyAdvocateAPI/ClickRedirect?client%3DChoice%26channel%3DGHPA%26placement%3Dlocaluniversal%26campaign%3D20691766300%26hotel%3DCA991%26destinationUrl%3Dhttps://www.choicehotels.com/CA991?checkInDate%3D2024-09-09%26checkOutDate%3D2024-09-10%26srp%3DSCPM%26adults%3D1%26minors%3D0%26mc%3DHAGOHPUS%26meta%3DPMFGPADUSSCPM_CA991_localuniversal_US_1_desktop_2024-09-09_selected_20691766300__paid%26gal%3D%26pmf%3Dhpagoogle%26gpa%3DGPADSCPM%26rooms%3D1%26gmp%3DMetaAd%26product%3Dlocaluniversal",
        "logo": "https://www.gstatic.com/travel-hotels/branding/051c58a9-b695-4fed-90ab-f02e340e4473.png",
        "official": true,
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 443",
        "extracted_lowest": 443
    },
        "total_rate": {
        "lowest": "R$ 443",
        "extracted_lowest": 443
    }
    },
    {
        "source": "Expedia.com",
        "link": "https://www.google.com/aclk?sa=l&ai=C7goPSFLeZs_hHsu-tOUP0tqz0A-qzc36c7q61aOrEtec7_fKRAgKEAMg6ZDtigEoBGDJhoCAyKOgGaABrOif-gLIAQiqBFZP0BhE8W5svnAS0HFAP4Yh2NxLV181bu6pE4OLulySLw5SNZnfA8oGhRL2QV6bvA3Bj9NdZ8LIMmFKSHQ-eLKELarHavvEg61ccu56t0tNN-rE3KbjYsAE5N3_j-MEiAX8r--hTcAFkgGgBmWQBwOoB-u4sQKoB6a-G6gHuZqxAqgH89EbqAfu0huoB_-csQKoB8rcG6gHkq-xAqgHu6SxAqgH2KaxAqgH3rWxAqgH26qxAqgH0KqxAqgH3LCxAqgH6rGxAqgHlLixAqgH7LixAqgHvrexAqgH28WxAqgHg8OxAqgHvK2xAqgHuauxAqgH6LWxAqAImBawCAHACAHSCCYQATICgEA6C-BhgsCAgICggIACQgEBSN641jJQCVjPpavY3bSIA8gJrAH4CQGiCoMGCgoyMDI0LTA5LTA5EAEaAlVTIgVwdC1CUilwS2bdLF3WijIPRXhwZWRpYVN0YW5kYXJkOAJIAVIJMzg2MDU5NjE4XWZml0JlMzNHQWgAcgNVU0SCAW8KCTMxNjQzODQ3MBo8CgJDMhI2cmF0ZXBsYW5pZD0zODYwNTk2MTgmbXBtPTI0Jm1wbj0zMTY0Mzg0NzAmbXBvPUVDJm1wcD0xGgoKAkMzEgR0cnVlGgkKAkM0EgNVU0QyAggBQgkzODYwNTk2MTiKAQ5iZXhfdXNfZGVza3RvcKABAbABAbgBAcgB5YDaL-ABAugBA_ABAfgBAqgCzAGoAuUDsAKs6J_6ArgC_K_voU3gAgDqAgNCUkz6AuwDaHR0cHM6Ly93d3cuZXhwZWRpYS5jb20vSG90ZWwtU2VhcmNoP3NlbGVjdGVkPTM1OTkmc3RhcnREYXRlPTIwMjQtMDktMDkmZW5kRGF0ZT0yMDI0LTA5LTEwJk1EUENJRD1VUy5NRVRBLkhQQS5IT1RFTC1DT1JFU0VBUkNILWRlc2t0b3AuSE9URUwmTURQRFRMPUhUTC4zNTk5LjIwMjQwOTA5LjIwMjQwOTEwLkRERi4xLkNJRC4yMDc0MDU2MDg5Mi5BVURJRC4uUlJJRC5iZXhfdXNfZGVza3RvcCZtY3RjPTEwJmN0PWhvdGVsJm1wZz1CUkwmbXBmPTQ5My40NyZtcGo9NjkuNzAmbXBsPVVTRCZleHBfcGc9Z29vZ2xlJmxhbmdpZD1wdCZhZD0xJnRwPSZ1dG1fc291cmNlPWdvb2dsZSZ1dG1fbWVkaXVtPWNwYyZ1dG1fdGVybT0zNTk5JnV0bV9jb250ZW50PWxvY2FsdW5pdmVyc2FsJnV0bV9jYW1wYWlnbj1Ib3RlbEFkcyZsYW5naWQ9MTAzMyZhZHVsdHM9MSZjaGlsZHJlbj0mcmF0ZXBsYW5pZD0zODYwNTk2MTgmbXBtPTI0Jm1wbj0zMTY0Mzg0NzAmbXBvPUVDJm1wcD0xigMA6AoBkAsDmAsB0Asa6AwTmg0BGqoNAlVTyA0BghQECHoSANAVAZgWAfgWAYAXAZIXCRIHCAEQAxjAAroXBDgBSAHQGAGIGQE&co=1&gclid=EAIaIQobChMIz6Wr2N20iAMVSx-tBh1S7Qz6EAoYAyABEgJvVfD_BwE&sig=AOD64_3YtO-k2oUJgwbQPgHjkUer4B5xMA&adurl=https://www.expedia.com/Hotel-Search?selected%3D3599%26startDate%3D2024-09-09%26endDate%3D2024-09-10%26MDPCID%3DUS.META.HPA.HOTEL-CORESEARCH-desktop.HOTEL%26MDPDTL%3DHTL.3599.20240909.20240910.DDF.1.CID.20740560892.AUDID..RRID.bex_us_desktop%26mctc%3D10%26ct%3Dhotel%26mpg%3DBRL%26mpf%3D493.47%26mpj%3D69.70%26mpl%3DUSD%26exp_pg%3Dgoogle%26langid%3Dpt%26ad%3D1%26tp%3D%26utm_source%3Dgoogle%26utm_medium%3Dcpc%26utm_term%3D3599%26utm_content%3Dlocaluniversal%26utm_campaign%3DHotelAds%26langid%3D1033%26adults%3D1%26children%3D%26rateplanid%3D386059618%26mpm%3D24%26mpn%3D316438470%26mpo%3DEC%26mpp%3D1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/ac238c97-1652-4830-8da8-bb8d8883af88.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    },
        "total_rate": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    }
    },
    {
        "source": "Priceline",
        "link": "https://www.google.com/aclk?sa=l&ai=COXxfSFLeZs_hHsu-tOUP0tqz0A-q-tzkdP-KyuKFEtai-PiQQggKEAQg6ZDtigEoBGDJhoCAyKOgGaAB2cSX-gLIAQiqBFZP0HU3_m1suXAS0HFAP4Yh2IYATng1bu6pE4OLulySLw5SE7LIGc4GhedPz1-bvA3Bj9NdCL2qG2BKTgBgL7KEN6jHavvEg6soLLl6t0tNN-qro8TKY8AE9pPb9MIEiAWqptPaTcAFkgGgBmWQBwOoB-u4sQKoB6a-G6gHuZqxAqgH89EbqAfu0huoB_-csQKoB8rcG6gHkq-xAqgHu6SxAqgH2KaxAqgH3rWxAqgH26qxAqgH0KqxAqgH3LCxAqgH6rGxAqgHlLixAqgH7LixAqgHvrexAqgH28WxAqgHg8OxAqgHvK2xAqgHuauxAqgH6LWxAqAImBawCAHACAHSCCYQATICgEA6C-BhgsCAgICggIACQgEBSN641jJQCVjPpavY3bSIA8gJrAH4CQGiCvcKCgoyMDI0LTA5LTA5EAEaAlVTIgVwdC1CUilwS2bdLF3WijIRUHJpY2VsaW5lU3RhbmRhcmQ4AkgBUgt3cVUyaUNYSFRPa12Fa5dCZQrXj0FoAHIDVVNEggG_AwoKNDUwMzgxMDQ1NxooCgJDMRIiJmNpdHlJRD0zMDAwMDAyMDM1JmFkdWx0cz0yJmxhbmQ9TBr6AgoCQzIS8wImbWV0YWlkPUlIRW9jQkFfRk5OcHNEbEZydDhkRXNudHJELUU3UXo1M3djMkVRYVdUQWE0MkFROUJiQUF4U1VPV09JZURIWllMcnlwbktPSzJ6UHBhUTI2Q2QyZWRCNWtvVnZpYks0cTBYd2dGU1JXY1EyTlpoWXNiaXZZRExSMjdyVlQ5MnUxMmNrMXo2MEh2S01EQVVGQ3AzUU9SR1ZSVkRCd2lHbElqUmhYM3F2RmpwRUZWQ25seXpCeGlxSzI4ZG83Nmk1V1Z0SHBLckRtSVNnMXNKRm1GcVo1RDdxSTRKMTBySGRkZHppUmZiNEUwaS10ZzdZYm9tOXpGMGFyLS1tbXlMY2M3MTN6VndGenM2Q0FuUlZadUxub1ZuQ05LNVhfT2tHX2lpc1c5TnFFOVFwUk9DQzgtOFhNUnJFbnA0d3ZYZk92OWhqdXN1TTFjVHJxOHRlZEhPSEc1c2JfYTNZNDEyM3hvNG04dWpLeGRVYzICCAFCBjExMTAwMKABAbABAbgBAcgBpMHjL-ABAugBBPABAfgBA6gCzAGoAuUDsALZxJf6ArgCqqbT2k3gAgDqAgNCUkz6ApwGaHR0cHM6Ly93d3cucHJpY2VsaW5lLmNvbS9yLz9jaGFubmVsPW1ldGEmcHJvZHVjdD1ob3RlbCZ0aGVtZT1naGFsaXN0aW5ncyZyZWZpZD1QTEdPT0dMRU1TUyZyZWZjbGlja2lkPVVTX0hQJTdDNDMxNjhfbG9jYWx1bml2ZXJzYWxfMSU3QzIwMjQwOTA5JTdDZGVza3RvcCU3Q3VzZXJkYXRlfHB1YmxpY3wyMDg1OTYzODU3MHx8MSU3QzElN0MwfDF8RU4maG90ZWxpZD00MzE2OCZjaGVja2luPTIwMjQwOTA5JmNoZWNrb3V0PTIwMjQwOTEwJnJvb21zPTEmY3VycmVuY3k9VVNEJmRpc3BsYXllZEN1cnI9QlJMJnBPU0NvdW50cnlDb2RlPVVTJnRheERpc3BsYXlNb2RlPUJQJmNpdHlJRD0zMDAwMDAyMDM1JmFkdWx0cz0yJmxhbmQ9TCZtZXRhaWQ9SUhFb2NCQV9GTk5wc0RsRnJ0OGRFc250ckQtRTdRejUzd2MyRVFhV1RBYTQyQVE5QmJBQXhTVU9XT0llREhaWUxyeXBuS09LMnpQcGFRMjZDZDJlZEI1a29WdmliSzRxMFh3Z0ZTUldjUTJOWmhZc2JpdllETFIyN3JWVDkydTEyY2sxejYwSHZLTURBVUZDcDNRT1JHVlJWREJ3aUdsSWpSaFgzcXZGanBFRlZDbmx5ekJ4aXFLMjhkbzc2aTVXVnRIcEtyRG1JU2cxc0pGbUZxWjVEN3FJNEoxMHJIZGRkemlSZmI0RTBpLXRnN1lib205ekYwYXItLW1teUxjYzcxM3pWd0Z6czZDQW5SVlp1TG5vVm5DTks1WF9Pa0dfaWlzVzlOcUU5UXBST0NDOC04WE1SckVucDR3dlhmT3Y5aGp1c3VNMWNUcnE4dGVkSE9IRzVzYl9hM1k0MTIzeG80bTh1akt4ZFVjJmRibGNudD10cnVlJnVzZXJfbnVtX2FkdWx0cz0xJnBkdGF4PTEwMC42NSZwZHQ9NTI0LjQ5JmxvY2FsZT1lbi11c4oDAOgKApALA5gLAdALGugME5oNARqqDQJVU8gNAYIUBAh6EgDQFQGYFgH4FgGAFwGSFwkSBwgBEAMYwAK6FwQ4AUgB0BgBiBkB&co=1&gclid=EAIaIQobChMIz6Wr2N20iAMVSx-tBh1S7Qz6EAoYBCABEgLSNvD_BwE&sig=AOD64_3jhnX3dtv4g7Yuc3-tBwgXMs5wLg&adurl=https://www.priceline.com/r/?channel%3Dmeta%26product%3Dhotel%26theme%3Dghalistings%26refid%3DPLGOOGLEMSS%26refclickid%3DUS_HP%257C43168_localuniversal_1%257C20240909%257Cdesktop%257Cuserdate%7Cpublic%7C20859638570%7C%7C1%257C1%257C0%7C1%7CEN%26hotelid%3D43168%26checkin%3D20240909%26checkout%3D20240910%26rooms%3D1%26currency%3DUSD%26displayedCurr%3DBRL%26pOSCountryCode%3DUS%26taxDisplayMode%3DBP%26cityID%3D3000002035%26adults%3D2%26land%3DL%26metaid%3DIHEocBA_FNNpsDlFrt8dEsntrD-E7Qz53wc2EQaWTAa42AQ9BbAAxSUOWOIeDHZYLrypnKOK2zPpaQ26Cd2edB5koVvibK4q0XwgFSRWcQ2NZhYsbivYDLR27rVT92u12ck1z60HvKMDAUFCp3QORGVRVDBwiGlIjRhX3qvFjpEFVCnlyzBxiqK28do76i5WVtHpKrDmISg1sJFmFqZ5D7qI4J10rHdddziRfb4E0i-tg7Ybom9zF0ar--mmyLcc713zVwFzs6CAnRVZuLnoVnCNK5X_OkG_iisW9NqE9QpROCC8-8XMRrEnp4wvXfOv9hjusuM1cTrq8tedHOHG5sb_a3Y4123xo4m8ujKxdUc%26dblcnt%3Dtrue%26user_num_adults%3D1%26pdtax%3D100.65%26pdt%3D524.49%26locale%3Den-us",
        "logo": "https://www.gstatic.com/travel-hotels/branding/icon_220.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 524",
        "extracted_lowest": 524,
        "before_taxes_fees": "R$ 472",
        "extracted_before_taxes_fees": 472
    },
        "total_rate": {
        "lowest": "R$ 524",
        "extracted_lowest": 524,
        "before_taxes_fees": "R$ 472",
        "extracted_before_taxes_fees": 472
    }
    }
    ],
    "prices": [
    {
        "source": "Quality Inn & Suites South San Jose / Morgan Hill",
        "link": "https://www.google.com/travel/clk?pc=AA80Osx_FDJ2W9gNN2Mzb33BVRcB3hCHg4Bc26I4loSiGs_UhLQjvLsiBgpmtQiYyne9-ADp8RGVAeGDhXXC3OUfaomxD82c6PVrCNVd1J5dw_XxFCFwUQHZWnn9ARte8ZZlt04M&pcurl=https://travel-productads.koddi.com/PropertyAdvocateAPI/ClickRedirect?client%3DChoice%26channel%3DGHPA%26placement%3Dlocaluniversal%26campaign%3D%26hotel%3DCA991%26destinationUrl%3Dhttps://www.choicehotels.com/CA991?checkInDate%3D2024-09-09%26checkOutDate%3D2024-09-10%26srp%3DSCPM%26adults%3D1%26minors%3D0%26mc%3DHAGOHPUS%26meta%3DPMFGPADUSSCPM_CA991_localuniversal_US_1_desktop_2024-09-09_selected___organic%26gal%3D%26pmf%3Dhpagoogle%26gpa%3DGPADSCPM%26rooms%3D1%26gmp%3DMetaOrganic%26product%3Dlocaluniversal&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/051c58a9-b695-4fed-90ab-f02e340e4473.png",
        "official": true,
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 443",
        "extracted_lowest": 443
    },
        "total_rate": {
        "lowest": "R$ 443",
        "extracted_lowest": 443
    }
    },
    {
        "source": "Best Hotel Deals",
        "link": "https://www.google.com/travel/clk?pc=AA80OsyHrhnTjByZYoeO9RnLvnxefmgitgx03EdD99jDGGUAjzOCjLn10R6eEfF8csJmpU-1us8T31blOOJCRYDJHIoAJj_Xw1JHHTsNHAe1VOxbad2y9XSde1QkdHzt70SJlLJmpR2W8w&pcurl=https://besthoteldealsglobal.com/property/17450?rooms%3D1%26currency%3DBRL%26withTaxes%3D%26startDate%3D09-09-2024%26endDate%3D10-09-2024%26occupancies%3D%5B%5D%26utm_source%3Dgoogle%26utm_medium%3DHAC%26utm_content%3D09_09_2024_1_localuniversal_17450_US_desktop_selected___1%26tp%3D408.66%26tt%3D61.58%26sup%3DNBCTGX%26flow%3D8FZq1B3vcdjUAhhKBsNJQt%252FapxncjB5TqZiJAZQjmbB68udjaiIxCIGFBu2KjvrO5s0DJ0OmqqwGoh4lorJqU%252Bus01CzTlI2%252BT%252B6gYdu2D8%253D%26g_rate%3Deddee14c-5d01-4afa-87d9-f1cc5e87fd11%26g_room%3Dt-bcfLEOrMM&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/49083cad-a022-48f3-965c-36ee20bf8352.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 409",
        "extracted_lowest": 409,
        "before_taxes_fees": "R$ 347",
        "extracted_before_taxes_fees": 347
    },
        "total_rate": {
        "lowest": "R$ 409",
        "extracted_lowest": 409,
        "before_taxes_fees": "R$ 347",
        "extracted_before_taxes_fees": 347
    }
    },
    {
        "source": "Traveluro",
        "link": "https://www.google.com/travel/clk?pc=AA80OsxGwcbazP-j1RzjpZgLgVUj9BulXcGFyZ6W3BA8PjWPLR9oD-_wQeEoRqsYKbvZzDCB0-MjTWlprdOwno8HIguSYNUYAkCBe2aLVNcFv44BXigpa4hnsSs-j4FITXwE&pcurl=https://pt.traveluro.com/hotel/321515?start_date%3D2024-09-09%26end_date%3D2024-09-10%26num_rooms%3D1%26ts%3DGoogle_US%26curr%3DBRL%26data_key%3DNjdfVVNEXzIwOF82M19Hb29nbGUyXzIwMjQtMDktMDkgMDA6NDQ6NDVfQkJfTl9fMF9ERVNLVE9QX182M19VU0RfMjAyNC0wOS0wOSAwMDo0NDo0NV82MF9VU0Q%3D%26mfa_data_key%3D%26PT%3D375.07%26PTF%3D41.65%26device%3Ddesktop%26party%3D%5B%5D%26user_country%3DUS%26user_lang%3Dpt%26num_adults%3D1%26site%3Dlocaluniversal%26rule%3DGoogle_US%26ads%3D%26PPA%3D0%26DD%3D0&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/aa5293d0-1acc-4289-ab28-fe6381cfbe5a.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 375",
        "extracted_lowest": 375
    },
        "total_rate": {
        "lowest": "R$ 375",
        "extracted_lowest": 375
    }
    },
    {
        "source": "Hotels Ugogo",
        "link": "https://www.google.com/travel/clk?pc=AA80OsyJ9z-CnXYkOD647vuZadq28XeoB-WP79OxKtyZtIFdoog_rnyOFKMASl01X5wMbUNO1RgqWATFYqQHar4U74Globxaw-O1_jUYUoWDhW4XGSWUw2tBK-eiqn7AgTuGMSOl&pcurl=https://hotelsugogo.com/property/17450?rooms%3D1%26currency%3DBRL%26withTaxes%3D%26startDate%3D09-09-2024%26endDate%3D10-09-2024%26occupancies%3D%5B%5D%26utm_source%3Dgoogle%26utm_medium%3DHAC%26utm_content%3D09_09_2024_1_localuniversal_17450_US_desktop_selected___1%26tp%3D408.66%26tt%3D61.58%26sup%3DNBCTGX%26flow%3Df4mWG4mQOS5nwXTn7UXY8lbKbTaoNpcPFsIY1gNWylETUJ37NUKMoNRQesstbtB0E6RCnKzKxJZOq0O26%252B9iznhzlig53whgSH83BcMeW1k%253D&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/7738061d-39a7-44cb-b511-bcc8c6aa33c6.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 409",
        "extracted_lowest": 409,
        "before_taxes_fees": "R$ 347",
        "extracted_before_taxes_fees": 347
    },
        "total_rate": {
        "lowest": "R$ 409",
        "extracted_lowest": 409,
        "before_taxes_fees": "R$ 347",
        "extracted_before_taxes_fees": 347
    }
    },
    {
        "source": "Booketta",
        "link": "https://www.google.com/travel/clk?pc=AA80OszGfHxMHYmgyq783lIjO6TfWDi5ARU_AcKsAmpj_ddd9k_XZCxaWyMxVP0R3FA2LJZD2PXWTzhZsc9LxCZzjhT27jWGCJNU4oVX7vqZtN0xk2RKRrAF0qrmMilsQRY&pcurl=https://booketta.com/property/17450?rooms%3D1%26currency%3DBRL%26withTaxes%3D%26startDate%3D09-09-2024%26endDate%3D10-09-2024%26occupancies%3D%5B%5D%26utm_source%3Dgoogle%26utm_medium%3DHAC%26utm_content%3D09_09_2024_1_localuniversal_17450_US_desktop_selected___1%26tp%3D408.66%26tt%3D61.58%26sup%3DNBCTGX%26flow%3DrAjsl24ODqv%252FVv6Lt00IUiNNbMjMu6NwVd9Vsj5%252FsP5S8sZuhyn5tvJzguwPPKgL093k%252Bb5MG11H1SM0l6y8vL%252BvSzppS7ePFaLeOqB4aFA%253D&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/72313411-61a2-48b3-a2e8-99552b38fe87.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 409",
        "extracted_lowest": 409,
        "before_taxes_fees": "R$ 347",
        "extracted_before_taxes_fees": 347
    },
        "total_rate": {
        "lowest": "R$ 409",
        "extracted_lowest": 409,
        "before_taxes_fees": "R$ 347",
        "extracted_before_taxes_fees": 347
    }
    },
    {
        "source": "PrimaStay",
        "link": "https://www.google.com/travel/clk?pc=AA80Osxzjs6JT58VOSpJmvP8pw0gqQ1WE2FASClvY99Guu0taAbOTkidamnKWQKPAFZEIraF7nTvpS2P2WljUmAv7c9-n7AKIVY7aWzoOnthP187AWOQyXNJ-PxxBMclaavq&pcurl=https://primastay.com/property/17450?rooms%3D1%26currency%3DBRL%26withTaxes%3D%26startDate%3D09-09-2024%26endDate%3D10-09-2024%26occupancies%3D%5B%5D%26utm_source%3Dgoogle%26utm_medium%3DHAC%26utm_content%3D09_09_2024_1_localuniversal_17450_US_desktop_selected___1%26tp%3D408.66%26tt%3D61.58%26sup%3DNBCTGX%26flow%3DDGKoVZjPDT9XegRSyyZ6%252F19oc8k7ID8Jdq5mstBDcuvnJJ2SGIzU31LpItK51NnMCypacSa5tU%252BvPrdwHxqwn6VK1LstPiWEgbhLYyosPmo%253D&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/e58e3c56-4b54-49e6-8e6b-2879b968103c.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 409",
        "extracted_lowest": 409,
        "before_taxes_fees": "R$ 347",
        "extracted_before_taxes_fees": 347
    },
        "total_rate": {
        "lowest": "R$ 409",
        "extracted_lowest": 409,
        "before_taxes_fees": "R$ 347",
        "extracted_before_taxes_fees": 347
    }
    },
    {
        "source": "Booking.com",
        "link": "https://www.google.com/travel/clk?pc=AA80Osz1ZKiaRkAiRbafPUjHdz0Na9ZgZEBOSSVimZFvu0huUBoj6RJYRAVxXJGzObCGuENSHVaskrA6nf8kDUw3xLsQ6-RgwhroOlG9Fi7OznKcUKjsDLmAwXIZbB0YdMxasxj1t0T7WAOm8Q&pcurl=https://www.booking.com/hotel/us/quality-inn-suites-south-san-jose-morgan-hill.html?checkin%3D2024-09-09%26checkout%3D2024-09-10%26group_adults%3D1%26req_adults%3D1%26show_room%3D48914308_352012041_2_1_0%26lang%3Dpt%26selected_currency%3DBRL%26exrt%3D5.59810019%26ext_price_total%3D493.49%26ext_price_tax%3D69.71%26xfc%3DUSD%26hca%3Dm%26group_children%3D0%26req_children%3D0%26%26no_rooms%3D1%26ts%3D1725843071%26edgtid%3DtSdaN9VTRGuAoEjM5uAFTA%26efpc%3DdbcKiLFaLw%26utm_source%3Dmetagha%26utm_medium%3Dlocaluniversal%26utm_campaign%3DUS%26utm_term%3Dhotel-489143%26utm_content%3Ddev-desktop_los-1_bw-1_dow-Monday_defdate-0_room-0_gstadt-1_rateid-public_aud-0_gacid-_mcid-10_ppa-0_clrid-0_ad-0_gstkid-0_checkin-20240909_ppt-%26aid%3D2127486%26label%3Dmetagha-link-LUUS-hotel-489143_dev-desktop_los-1_bw-1_dow-Monday_defdate-0_room-0_gstadt-1_rateid-public_aud-0_gacid-_mcid-10_ppa-0_clrid-0_ad-0_gstkid-0_checkin-20240909_ppt-&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/icon_184.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    },
        "total_rate": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    }
    },
    {
        "source": "KAYAK.com",
        "link": "https://www.google.com/travel/clk?pc=AA80OszrkTFuG9s8U_0_lqoXtpMdNJJMftnnKa8XlKW08HFDnn1MH_H3vVU1KQCXHHDc9TP1bXokTdV4FDaqaDsiSbNf_mIfQwYtOEXrlTwgL-IRrNmnYhtt6IHVHy0UKbR8SA&pcurl=https://www.kayak.com/semi/ha/hotel_ads/3030384/en.html?utm_source%3Dgoogle%26utm_medium%3Dcpc%26utm_term%3D3030384%26adType%3D0%26utm_content%3Dlocaluniversal%26utm_campaign%3DHotelAds%26ci%3D2024-09-09%26co%3D2024-09-10%26gs%3Dlocaluniversal%26l%3D1%26pc%3DUSD%26rid%3D%26pdtax%3D44.17%26pdtotal%3D477.57%26rpid%3D%26uc%3DUS%26ucuc%3DBRL%26d%3Ddesktop%26lc%3Dpt%26v%3Dfalse%26rrid%3Dkayak-us%26k_pc%3D~Q1RSSVBIT1RFTEVOSEM%26k_rt%3D%26k_sid%3DnkAEEborKN%26k_kct%3D1725831692%26k_gct%3D1725833633%26g%3D2%26r%3D1%26ac%3D2%26k_cc%3Dus%26dt%3Dselected%26cmpid%3D%26cmptrack%3D&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/6417d44c-c676-4b16-8ae9-f3bbeaa51899.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 478",
        "extracted_lowest": 478,
        "before_taxes_fees": "R$ 434",
        "extracted_before_taxes_fees": 434
    },
        "total_rate": {
        "lowest": "R$ 478",
        "extracted_lowest": 478,
        "before_taxes_fees": "R$ 434",
        "extracted_before_taxes_fees": 434
    }
    },
    {
        "source": "Trip.com",
        "link": "https://www.google.com/travel/clk?pc=AA80OsxWleFlKn_mFfGi5kDagL3jOi1TSEgrNmJnU63zVWdDW-gIOieD901MTs7Smf2ioqXNB0y1J5NXpzjM7K4ehiVxt_rR1IbMHDKOhn9_WHcCoKe-hgxmbXewVBo&pcurl=https://us.trip.com/hotels/redirect?hotelid%3D2904254%26city%3D26886%26shoppingid%3DM3s7ne18gDeF%26adult%3D1%26children%3D0%26ages%3D%26checkin%3D2024-09-09%26checkout%3D2024-09-10%26curr%3DBRL%26Allianceid%3D15214%26Sid%3D1209572%26prid%3DLANG_US%26trip_sub1%3D09_09_2024_1_localuniversal_2904254_US_desktop_selected___0_0-LANG_US-landing%26hpaopts%3Dstand%5BH4sIAAAAAAAA_-OK4GKSYBJi4mCU8uNYeWfpQxYhLkMzQwNTIzNTAyOD1YwWKyUCOGYw7lu4kXEHI9MBRqaLjNUi69wfRgU7PGRcUmDLdT041OEF4-TL008xdrIYg8Fh-wUsjFI8vsbF5nmphhbpLqluCowaG7p_bmPzYAxiM3G1NLA0iZLhYg4NdhE05FgjE5Ui4yAF4inCeEmsqXm6ocEZ8wS6GJk8GFcxsnMxGxgZCjB9YuRHNjfe0BAANOOunsMAAAA%5Dfgt%5B1%5D%26br%3D477.57%26tf%3D44.17%26display%3Dexavg%26locale%3Den_us%26%26_roomid%3D1277783593&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/b8089cb0-34b4-458c-8749-9bc4614baba4.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 478",
        "extracted_lowest": 478
    },
        "total_rate": {
        "lowest": "R$ 478",
        "extracted_lowest": 478
    }
    },
    {
        "source": "Priceline",
        "link": "https://www.google.com/travel/clk?pc=AA80OswetpoY3ZAzchRaIvkfwOhttKmv9ColYVZ8GcFn9kokWeZcPC8n0XoEKuuOyScHB-Vh6qpPw9ZwzJnKayAKko9ja4Jwz_mVf136gPdamDlNJ4jSPUboNCtcbb6pj2nwH2upUHmxKck&pcurl=https://www.priceline.com/r/?channel%3Dmeta%26product%3Dhotel%26theme%3Dghalistings%26refid%3DPLGOOGLEMSS%26refclickid%3DUS_HP%257C43168_localuniversal_1%257C20240909%257Cdesktop%257Cuserdate%7Cpublic%7C%7C%7C1%257C1%257C0%7C0%7CEN%26hotelid%3D43168%26checkin%3D20240909%26checkout%3D20240910%26rooms%3D1%26currency%3DUSD%26displayedCurr%3DBRL%26pOSCountryCode%3DUS%26taxDisplayMode%3DBP%26cityID%3D3000002035%26adults%3D2%26land%3DL%26metaid%3DIHEocBA_FNNpsDlFrt8dEsntrD-E7Qz53wc2EQaWTAa42AQ9BbAAxSUOWOIeDHZYLrypnKOK2zPpaQ26Cd2edB5koVvibK4q0XwgFSRWcQ2NZhYsbivYDLR27rVT92u12ck1z60HvKMDAUFCp3QORGVRVDBwiGlIjRhX3qvFjpEFVCnlyzBxiqK28do76i5WVtHpKrDmISg1sJFmFqZ5D7qI4J10rHdddziRfb4E0i-tg7Ybom9zF0ar--mmyLcc713zVwFzs6CAnRVZuLnoVnCNK5X_OkG_iisW9NqE9QpROCC8-8XMRrEnp4wvXfOv9hjusuM1cTrq8tedHOHG5sb_a3Y4123xo4m8ujKxdUc%26dblcnt%3Dtrue%26user_num_adults%3D1%26pdtax%3D100.65%26pdt%3D524.49%26locale%3Den-us&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/icon_220.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 524",
        "extracted_lowest": 524,
        "before_taxes_fees": "R$ 472",
        "extracted_before_taxes_fees": 472
    },
        "total_rate": {
        "lowest": "R$ 524",
        "extracted_lowest": 524,
        "before_taxes_fees": "R$ 472",
        "extracted_before_taxes_fees": 472
    }
    },
    {
        "source": "Agoda",
        "link": "https://www.google.com/travel/clk?pc=AA80OswsMRhut06rUrNPU6XCDcTXHs7OPD-XCqyid4YMm5RTOjpx0ZyAXwjYLZ1nImXGvgtRj-MUkvTUM3EDU_rb1Ib3n0aq0fGXiAST8OxGRP_L9ZOxG2uHsAgHsG7Nh4lQJFjrhg&pcurl=https://www.agoda.com/pt-pt/partners/partnersearch.aspx?site_id%3D1917614%26CkInDay%3D09%26CkInMonth%3D09%26CkInYear%3D2024%26CkOutDay%3D10%26campaignid%3D%26CkOutMonth%3D09%26CkOutYear%3D2024%26SearchDateType%3Dselected%26NumberOfAdults%3D1%26LT%3D1%26NumberOfChildren%3D0%26childages%3D%26NumberOfRooms%3D1%26gsite%3Dlocaluniversal%26los%3D1%26PartnerCurrency%3DUSD%26hid%3D184875%26RoomID%3D503810457%26masterRoomId%3D503810457%26PriceTax%3D102.73%26PriceTotal%3D526.56%26RatePlan%3D531b0afc-ac1b-a1c6-d5f8-51e1b2d8320a%26UserCountry%3DUS%26Currency%3DBRL%26UserDevice%3Ddesktop%26Verif%3Dfalse%26rr%3Drow_desktop%26audience_list%3D%26mcid%3D28081%26booking_source%3Dcpc%26adType%3D0%26push_id%3D%26original_rr%3D&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/b13642de-d476-41bd-8254-3edc2e567aa6.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 527",
        "extracted_lowest": 527,
        "before_taxes_fees": "R$ 474",
        "extracted_before_taxes_fees": 474
    },
        "total_rate": {
        "lowest": "R$ 527",
        "extracted_lowest": 527,
        "before_taxes_fees": "R$ 474",
        "extracted_before_taxes_fees": 474
    }
    },
    {
        "source": "Vio.com",
        "link": "https://www.google.com/travel/clk?pc=AA80OsxItfbdWz-hA0rUqlEmO9OYTa0Yk3fZ1CEpzlZcBFkb9ykDklUb3sVEqwdWIcSpoLL1eoe7GSgsTDSVL3NakOM-4pKUzLTPmpKAio12lxsFsPb0GvsE8TJMdMjBExsK&pcurl=https://deals.vio.com/?sig%3D73aca13c7f952d2641c156f3e69125e1eb497c325f122828ee5aa8797168b9a12d32303331333438363233%26turl%3Dhttps%253A%252F%252Fwww.vio.com%252FHotel%252FSearch%253FcheckIn%253D2024-09-09%2526checkOut%253D2024-09-10%2526curr%253DBRL%2526forceCurrencyChange%253D1%2526lang%253Dpt%2526forceLanguageChange%253D1%2526rooms%253D1%253A%2526utm_source%253Dgha%2526utm_medium%253Dcpc%2526hotelId%253D5266660%2526userCountry%253DUS%2526profile%253Dr2d2m73kn8%2526preferredRate%253D477.57%2526label%253Dsrc%25253Dgha%252526cltype%25253Dhotel%252526datype%25253Dselected%252526gsite%25253Dlocaluniversal%252526ucountry%25253DUS%252526udevice%25253Ddesktop%252526hotel%25253D5266660%252526day%25253D09%252526month%25253D09%252526year%25253D2024%252526los%25253D1%252526price%25253D477.57%252526currency%25253DBRL%252526cid%25253D%252526listid%25253D%252526rateid%25253DUS%252526closerateid%25253D%252526promo%25253D0%252526isPrivateRate%25253D0%252526isAudienceUser%25253D0%252526isPaidClick%25253D0%252526_th%25253Dca4c0fa686ae8a115bda361b10cb07ea9d94adbf5ea94941%252526dts%25253D1725842815%252526query%25253D3%2526epv%253DMS4y%2526esd%253DiHWcIusBnrlKhVlc8C_SyKoveItyqm49lmj1YEI4Tm7Jv9JuCTAzlcbz2XxFN9U0E_20ejPVBTJX0067GsCdSAmufMg6U0jDn7t-bbfG-VLMMg%25253D%25253D%2526oti%253D74iQoZ2wAWU%2526token%253Dkhsk22&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/0a94aea5-2080-4e1a-971b-c5da9d06fc63.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 478",
        "extracted_lowest": 478,
        "before_taxes_fees": "R$ 434",
        "extracted_before_taxes_fees": 434
    },
        "total_rate": {
        "lowest": "R$ 478",
        "extracted_lowest": 478,
        "before_taxes_fees": "R$ 434",
        "extracted_before_taxes_fees": 434
    }
    },
    {
        "source": "Expedia.com",
        "link": "https://www.google.com/travel/clk?pc=AA80Osy-tDbqBmxOCoMC1jnN4FVLtIgrBZllhUslzJDnWVt9pekVRfw6vHeb8jNzdgzh0YRror3GBthVBv98-iiQCXjCdJxi-O0izRvlH14FNmrXWYSH-I2lezKH-gbJvzkA3B0TVPTX&pcurl=https://www.expedia.com/Hotel-Search?selected%3D3599%26startDate%3D2024-09-09%26endDate%3D2024-09-10%26MDPCID%3DUS.META.HPA.HOTEL-ORGANIC-desktop.HOTEL%26MDPDTL%3DHTL.3599.20240909.20240910.DDF.1.CID..AUDID..RRID.bex_us_desktop%26mctc%3D10%26ct%3Dhotel%26mpg%3DBRL%26mpf%3D493.47%26mpj%3D69.70%26mpl%3DUSD%26exp_pg%3Dgoogle%26langid%3Dpt%26ad%3D1%26tp%3D%26utm_source%3Dgoogle%26utm_medium%3Dcpc%26utm_term%3D3599%26utm_content%3Dlocaluniversal%26utm_campaign%3DHotelAds%26langid%3D1033%26adults%3D1%26children%3D%26rateplanid%3D386059618%26mpm%3D24%26mpn%3D316438470%26mpo%3DEC%26mpp%3D1&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/ac238c97-1652-4830-8da8-bb8d8883af88.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    },
        "total_rate": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    }
    },
    {
        "source": "Hotels.com",
        "link": "https://www.google.com/travel/clk?pc=AA80OsxIehJNbKSLEPJblD8FECACqyIizwMzJllHAT7eiiKVBf56xq-rdmvXvMone2LRb-duNbXZw-LN3lpMnduy1Oxzigb7E39ERJEyC0iRNJBa8NDUVsQvSI-_oMShyXCk_Bebb5o&pcurl=https://www.hotels.com/Hotel-Search?selected%3D3599%26startDate%3D2024-09-09%26endDate%3D2024-09-10%26mdpcid%3DHCOM-US.META.HPA.HOTEL-ORGANIC-desktop.HOTEL%26MDPDTL%3DHTL.3599.20240909.20240910.DDF.1.CID..AUDID.%26adults%3D1%26children%3D%26mctc%3D10%26mpf%3D493.47%26mpg%3DBRL%26mpl%3DUSD%26mpj%3D69.70%26rffrid%3Dsem.hcom.US.156.024.localuniversal.02.desktop-1.kwrd%3DGGMETA.3599USpt-20240909-N-ABW%3D1-camp%3D-aud%3D-N%26rateplanid%3D386059618%26mpm%3D24%26mpn%3D316438470%26mpo%3DEC%26mpp%3D1%26locale%3Den_US%26siteid%3D300000001&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/f358dd45-ebd1-4af8-988d-d53154b73975.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    },
        "total_rate": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    }
    },
    {
        "source": "momondo.com",
        "link": "https://www.google.com/travel/clk?pc=AA80OswKt71MRtW3kwHW1tQpMrqXhkMVjzptGAW5U8w6ZtGedJWSJTlZu3ZIQvpF8W55qPyb2CRsFkNtkCo7oXYPidLFFqCw1zj6ucmReRrqw1G0eZZWTAOpKnzov6COdA&pcurl=https://www.momondo.com/semi/ha/hotel_ads/3030384/en.html?utm_source%3Dgoogle%26utm_medium%3Dcpc%26utm_term%3D3030384%26adType%3D0%26utm_content%3Dlocaluniversal%26utm_campaign%3DHotelAds%26ci%3D2024-09-09%26co%3D2024-09-10%26gs%3Dlocaluniversal%26l%3D1%26pc%3DUSD%26rid%3D%26pdtax%3D44.17%26pdtotal%3D477.57%26rpid%3D%26uc%3DUS%26ucuc%3DBRL%26d%3Ddesktop%26lc%3Dpt%26v%3Dfalse%26rrid%3Dkayak-us%26k_pc%3D~Q1RSSVBIT1RFTEVOSEM%26k_rt%3D%26k_sid%3DnkAkLPG0oR%26k_kct%3D1725831692%26k_gct%3D1725833604%26g%3D2%26r%3D1%26ac%3D2%26k_cc%3Dus%26dt%3Dselected%26cmpid%3D%26cmptrack%3D&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/icon_970163321.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 478",
        "extracted_lowest": 478,
        "before_taxes_fees": "R$ 434",
        "extracted_before_taxes_fees": 434
    },
        "total_rate": {
        "lowest": "R$ 478",
        "extracted_lowest": 478,
        "before_taxes_fees": "R$ 434",
        "extracted_before_taxes_fees": 434
    }
    },
    {
        "source": "goseek.com",
        "link": "https://www.google.com/travel/clk?pc=AA80Osxr-njbnTDMrGlRJzOcoVxuN8BU4dOLj1JTioJqOnXvk0selyKZZCiPvcjbwPmPgJNumOK9k7CX5f-np4K_hV20xI3D4d6uQxM8LZfPP2-yxGhmcqexq_BxDk-4Zrd_8idme5mPXg&pcurl=https://deals.goseek.com/?sig%3D34c47a49c3e8320dca5a5dda0da2a460c1bc23afde972e2894a4ff0b6052fb53383131303836353931%26turl%3Dhttps%253A%252F%252Fwww.goseek.com%252FHotel%252FSearch%253FcheckIn%253D2024-09-09%2526checkOut%253D2024-09-10%2526curr%253DBRL%2526forceCurrencyChange%253D1%2526lang%253Dpt%2526forceLanguageChange%253D1%2526rooms%253D1%253A%2526utm_source%253Dgha%2526utm_medium%253Dcpc%2526hotelId%253D5266660%2526userCountry%253DUS%2526profile%253Dr2d2m73kn8%2526preferredRate%253D477.57%2526label%253Dsrc%25253Dgha%252526cltype%25253Dhotel%252526datype%25253Dselected%252526gsite%25253Dlocaluniversal%252526ucountry%25253DUS%252526udevice%25253Ddesktop%252526hotel%25253D5266660%252526day%25253D09%252526month%25253D09%252526year%25253D2024%252526los%25253D1%252526price%25253D477.57%252526currency%25253DBRL%252526cid%25253D%252526listid%25253D%252526rateid%25253DUS%252526closerateid%25253D%252526promo%25253D0%252526isPrivateRate%25253D0%252526isAudienceUser%25253D0%252526isPaidClick%25253D0%252526_th%25253Dca4c0fa686ae8a115bda361b10cb07ea9d94adbf5ea94941%252526dts%25253D1725842815%252526query%25253D3%2526epv%253DMS4y%2526esd%253DiHWcIusBnrlKhVlc8C_SyKoveItyqm49lmj1YEI4Tm7Jv9JuCTAzlcbz2XxFN9U0E_20ejPVBTJX0067GsCdSAmufMg6U0jDn7t-bbfG-VLMMg%25253D%25253D%2526oti%253D74iQoZ2wAWU%2526token%253Dkhsk22&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/4bb2e6d3-77b1-4306-8c69-5a97ce95ce39.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 478",
        "extracted_lowest": 478,
        "before_taxes_fees": "R$ 434",
        "extracted_before_taxes_fees": 434
    },
        "total_rate": {
        "lowest": "R$ 478",
        "extracted_lowest": 478,
        "before_taxes_fees": "R$ 434",
        "extracted_before_taxes_fees": 434
    }
    },
    {
        "source": "Stayforlong.com",
        "link": "https://www.google.com/travel/clk?pc=AA80OsxSnsqi1UqnimLwh0cXZrwMulacU-QcrOgQ522NrVHtTwj4UjpVvc-xzBHFlzkLGykZpXILsEkJZkN5BdlHKbipzFpR_qYJYyHUDNxreEGRkoYVjTqehcZfwAVBg56_M50ZmQ&pcurl=https://www.stayforlong.com/hotel-detail/85913?checkin%3D2024-09-09%26checkout%3D2024-09-10%26adults%3D1%26metadevice%3Ddesktop%26aid%3Dgha%26market%3Dus%26lang%3Dpt%26total%3D86%26query_type%3Dpull%26ch%3D0%26adclick%3Dfree%26defaultdate%3Dfalse%26googlesite%3Dlocaluniversal%26p%3D4f6b46716145b682b89cdf6f7f778bcf%26r%3Dd26bd7943b44bb4b392ebe07df52ee0c%26source%3Dmeta&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/b322e53d-e0bf-472d-a84a-9109628ca027.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 481",
        "extracted_lowest": 481
    },
        "total_rate": {
        "lowest": "R$ 481",
        "extracted_lowest": 481
    }
    },
    {
        "source": "Reservations.com",
        "link": "https://www.google.com/travel/clk?pc=AA80Osx3bhLd_tvUTJCSSpkOAPobWAYj5HtRF0VXIxJd82LNzo5EDvqy_lfH2nAamKKQzKKRZMrMGpSoEg7LXnA9ZUhLsyhEkUTNDESU2mBjgy4Bklak3vp8S6xrzIuwJ2q10hWK&pcurl=https://www.reservations.com/hotel/quality-inn-morgan-hill?rc-ar%3D09-09-2024%26rc-de%3D09-10-2024%26rc-ro%3D1%26rc-ppid%3D3%26rmcid%3DHA%26rc-rm%3D2%26cn%3DPS_20240908T202642_mELXZoYxxn3U%252BOSLr9GvaSZ5Hm0GG9vjZVNHo9IXFMs%253D&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/1409d146-3224-4553-84a0-d675b44688ca.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 473",
        "extracted_lowest": 473
    },
        "total_rate": {
        "lowest": "R$ 473",
        "extracted_lowest": 473
    }
    },
    {
        "source": "br.Bluepillow.com",
        "link": "https://www.google.com/travel/clk?pc=AA80OswxsR84Hrih_EDZiynPCzAdG9YjY0TazdUqeyJBtJeNVOtSUfRaekSo68CKtiQIOU8iPSPFf49P_9Fl5ew0k5BgVfEsY1ChITKKY3iIddNQ6_PTYzZ8h8_RLHDODvolPQ&pcurl=https://br.bluepillow.com/checkout/648085681f174c2170a02ddd?begin%3D2024-09-09%26end%3D2024-09-10%26block_id%3D-b,vRihpCme8DLVkdtS7kSqwKe2LQ0zN7Nonnhe4qdguSj82DPp5KT9_7LKoOof3BXZ,-bpctrip-Hotel%26adults%3D1%26childs%3D0%26infants%3D0%26childrens%3D0%26country%3DUS%26currency%3DBRL%26language%3Dpt%26source%3Dlocaluniversal%26utm_campaign%3Dhotel%26prid%3D&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/c770e909-af04-45dd-8ad7-335bc5055826.png",
        "num_guests": 1,
        "rate_per_night": {
        "lowest": "R$ 478",
        "extracted_lowest": 478
    },
        "total_rate": {
        "lowest": "R$ 478",
        "extracted_lowest": 478
    }
    },
    {
        "source": "Travelocity.com",
        "link": "https://www.google.com/travel/clk?pc=AA80Osxcr7GRCLXASqUF_R2UQ1cmul5GV-IbFo5cuDr80Hvkji2RwW5JwIwrrq6fj1K0Mkb8O5fg5caMPhsv81dRr-Jo4WW3ZMPI8S7cyAFpircgELWg1_MxxkOsjnyTUoSGQthE4c9Y&pcurl=https://www.travelocity.com/Hotel-Search?selected%3D3599%26startDate%3D2024-09-09%26endDate%3D2024-09-10%26adults%3D1%26children%3D%26MDPCID%3Dtravelocity-US.META.HPA.ORGANIC.HOTEL%26MDPDTL%3DHTL.3599.20240909.20240910.DDF.1.CID..AUDID..localuniversal%26exp_curr%3DBRL%26exp_dp%3D493.47%26exp_tx%3D69.70%26mctc%3D10%26rateplanid%3D386059618%26mpm%3D24%26mpn%3D316438470%26mpo%3DEC%26mpp%3D1&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/icon_185.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    },
        "total_rate": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    }
    },
    {
        "source": "Wego",
        "link": "https://www.google.com/travel/clk?pc=AA80OsyCoWAALER5kBOsG9TAcOFKEH3Dsjs5DgrOYBbRk7ibvCLNP6DLxPPuTsLLiDAFYlUktTUOj7PlFRMCAt081MObrcC_KCpLf8bzC3EgLnMamaXE2udXKgOWhg&pcurl=https://www.wego.com/hotels/searches/9163/2024-09-09/2024-09-10/470200?guests%3D1%26wego_hotel_id%3D470200%26bow_only%3Dtrue%26%26ts_code%3D2f2fc%26wg_source%3Dmeta_distribution%26wg_medium%3Dgha%26wg_campaign%3Dfbl%26ulang%3Dpt%26wego_currency%3DBRL%26wego_price%3DVVNEOjg2LjMxOjBlYmUzYjRiY2QzYzgwYmU5NzRhMDY4YmQ5MTE1MzU1Yjg1NzFmNTdhYjFlOGE3NDZiYTExNzY2NjVmMDE5ZDU6YXB5YWN3Z3Bhd3E6MTcyNTg0Mjc4Ng%253D%253D%26provider%3DaG90ZWxzLndlZ28uY29t&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/056bc8ad-af15-4ca3-b777-4ad816966428.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 483",
        "extracted_lowest": 483
    },
        "total_rate": {
        "lowest": "R$ 483",
        "extracted_lowest": 483
    }
    },
    {
        "source": "Travelated - EUA",
        "link": "https://www.google.com/travel/clk?pc=AA80OsxLU9rtGn0Mc9HzcZhL8VcJPCxTCntm9k80SHfmJXUlEitzus1YZv_FdpFgxijtcyR20lEZhB-bdS1dFNzHY4OKS0JY1lNTFAnexwC_cvQDp-syRFHGIMGoiX0VRMnkgPVPJwP9m4FuwDur22DtUw&pcurl=https://www.travelated.com/search/listing?hotel%3D82327324%26destinationType%3D3%26checkIn%3D2024-09-09%26checkOut%3D2024-09-10%26adults%3D1%26childrenAge%3D%26rooms%3D1%26roomId%3D5:1soFTKl6HGa5O%26planId%3D26,57fbcb3f65b87a42ce000002_2_1_5:1soFTKl6HGa5O_r1%26priceTotal%3D670.48%26priceTax%3D89.40%26utm_campaign%3D%26utm_source%3Dghf_localuniversal_hotel%26country%3DUS%26currency%3DBRL%26language%3Dpt%26utm_medium%3Dgooglehotels%26extraRooms%3D%26breakdown%3DUSD%7C12.98:0.00:0.00%7C0.00:2.99:0.00%7C119.77%7C2&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/8108006399203336476.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 670",
        "extracted_lowest": 670,
        "before_taxes_fees": "R$ 598",
        "extracted_before_taxes_fees": 598
    },
        "total_rate": {
        "lowest": "R$ 670",
        "extracted_lowest": 670,
        "before_taxes_fees": "R$ 598",
        "extracted_before_taxes_fees": 598
    }
    },
    {
        "source": "Orbitz.com",
        "link": "https://www.google.com/travel/clk?pc=AA80OswyBKHThAhQgS1f2NlpstGblBr2TpC3wwU5wFjNVDMQbd3TpMdOmqCawbWexVBKozKhoifMSrqNnIEc0I3MABVYcRe8gNA-FxQaOLjbPRX8wWShxyw3Evcs2LDJx_e7BypAxQk&pcurl=https://www.orbitz.com/Hotel-Search?selected%3D3599%26startDate%3D2024-09-09%26endDate%3D2024-09-10%26adults%3D1%26children%3D%26MDPCID%3DORBITZ-US.META.HPA.ORGANIC-desktop.HOTEL%26MDPDTL%3Dhtl.3599.20240909.20240910.DDF.1.CID..AUDID..localuniversal%26exp_curr%3DBRL%26exp_dp%3D493.47%26exp_tx%3D69.70%26mctc%3D10%26rateplanid%3D386059618%26mpm%3D24%26mpn%3D316438470%26mpo%3DEC%26mpp%3D1&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/icon_100640403.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    },
        "total_rate": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    }
    },
    {
        "source": "CheapTickets.com",
        "link": "https://www.google.com/travel/clk?pc=AA80OszE_TxZqy4f7PU5OlUJJt-GBaSWzFpnlEmnGrP7y4b7WLQ_qTcHK1chdXnG8mFUz5lbNd9QstCT86q0AY9NxIfLO-mCo22OVa0UzP_f_Hh4wi3udX8Yg9XdZU-Fp8t3yvV1zD55ldxME4w&pcurl=https://www.cheaptickets.com/Hotel-Search?selected%3D3599%26startDate%3D2024-09-09%26endDate%3D2024-09-10%26MDPCID%3DCHEAPTICKETS-US.META.HPA.ORGANIC-desktop.HOTEL%26langid%3D1033%26adults%3D1%26children%3D%26MDPDTL%3Dhtl.3599.20240909.20240910.DDF.1.CID..AUDID..localuniversal%26exp_curr%3DBRL%26exp_dp%3D493.47%26exp_tx%3D69.70%26mctc%3D10%26rateplanid%3D386059618%26rateplanid%3D386059618%26mpm%3D24%26mpn%3D316438470%26mpo%3DEC%26mpp%3D1&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/736aa480-c3ae-4b4d-ac2a-1c658f70d17a_%5BUngrouped%5D.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    },
        "total_rate": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 441",
        "extracted_before_taxes_fees": 441
    }
    },
    {
        "source": "eDreams",
        "link": "https://www.google.com/travel/clk?pc=AA80OsxmqYtJTJ0gX_oKW6R4b2j1F9MLTzqK1NNHSpsU3s7whBqvm-bsXXQUkdisIvanGMP3wJnzjQENA1RCKIDg1NS-xe46XaFitE0ZVn5kL-mODbDfF1DC9Qtpf6dk&pcurl=https://accommodation.edreams.net/Hotel-Search?selected%3D3599%26startDate%3D2024-09-09%26endDate%3D2024-09-10%26adults%3D1%26children%3D%26mpf%3D493.47%26cur%3DBRL%26mpj%3D69.70%26wapb3%3D%7Cc.506950%7Cl.en_US%7Ct.meta%7Cs.ghs%26MDPCID%3DeDreams-US.DPS.Edreams.MetaSearch-GHA.HOTEL%26mpl%3DUSD%26numberOfRooms%3D1%26locale%3Den_US%26rffrid%3Dh4p.hcom.US.ghaodigeo.000.000.kwrd%3D%26pos%3DEDREAMS_US%26rateplanid%3D386059618%26utm_medium%3Dmetasearch%26utm_campaign%3D209623%26utm_term%3Dhotel%26utm_source%3DFREEBOOKING&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/05208793-cd83-4e8b-b76c-7ff346e093e3.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 449",
        "extracted_before_taxes_fees": 449
    },
        "total_rate": {
        "lowest": "R$ 493",
        "extracted_lowest": 493,
        "before_taxes_fees": "R$ 449",
        "extracted_before_taxes_fees": 449
    }
    },
    {
        "source": "HomeToGo",
        "link": "https://www.google.com/travel/clk?pc=AA80OswYBiFZR5_tFT0cytE2h0xmG7Ki6ICHkM7MQ1JebbCimLzs2XfhMIf0_29VnBEfZv35tHWVTD7HJqtxp_G905QlSB30iRPFwVgCDd9eQmUo8paAOy7ybZztHdYkGN0&pcurl=https://www.hometogo.com/search/5460ae092d57a?arrival%3D2024-09-09%26duration%3D1%26id%3Dza4d09894b8059cf3%2540f%2540s%26adults%3D1%26tpp%3Dgsmghfq.66db04e0%26adword%3Dghf/us/za4d09894b8059cf3/ct%3Dhs/n-a/n-a/5460ae092d57a%26utm_source%3Dghf%26hl%3Dpt_US%26ghf_acc%3Dhtg_ghf%26c%3DBRL%26type%5B%5D%3Dhotel&ap=1",
        "logo": "https://www.gstatic.com/travel-hotels/branding/5bde3665-f4fd-4468-b4bf-dee085aaacce.png",
        "num_guests": 2,
        "rate_per_night": {
        "lowest": "R$ 530",
        "extracted_lowest": 530
    },
        "total_rate": {
        "lowest": "R$ 530",
        "extracted_lowest": 530
    }
    }
    ],
    "typical_price_range": {
    "lowest": "R$ 327",
    "extracted_lowest": 327,
    "highest": "R$ 408",
    "extracted_highest": 408
},
    "nearby_places": [
    {
        "category": "Ponto de interesse",
        "name": "Gilroy Gardens Family Theme Park",
        "link": "https://www.google.com/search?q=Gilroy+Gardens+Family+Theme+Park&stick=H4sIAAAAAAAAAONgFmJQ4tTP1TewqLA0SddSzE620s_JT04syczPgzOsEktKihKTQcxiALqjwc0yAAAA#fpstate=trskp&trifp=kpq%253DGilroy%252BGardens%252BFamily%252BTheme%252BPark%2526skpm%253D/m/08x94g%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipPh8o8POl6v17yWDjRYSv-fZqQIycOvBYUlBAC-=w213-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "20 min"
        }
        ],
        "rating": 4.5,
        "reviews": 5387,
        "gps_coordinates": {
        "latitude": 37.0049962,
        "longitude": -121.62906059999999
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Henry W. Coe State Park",
        "link": "https://www.google.com/search?q=Henry+W.+Coe+State+Park&stick=H4sIAAAAAAAAAONgFmJQ4tTP1TcwSSsuzNFSzE620s_JT04syczPgzOsEktKihKTQcxiABGZI1QyAAAA#fpstate=trskp&trifp=kpq%253DHenry%252BW.%252BCoe%252BState%252BPark%2526skpm%253D/m/04fsql%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipM4WXTQSUnZp7B0MImcebjHfx12gNO3tsUOBqQo=w160-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "32 min"
        }
        ],
        "rating": 4.6,
        "reviews": 712,
        "description": "Parque com mais de 35 mil hectares de área selvagem, cânions e leões-da-montanha.",
        "gps_coordinates": {
        "latitude": 37.1867545,
        "longitude": -121.5471647
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Wings of History Air Museum",
        "link": "https://www.google.com/search?q=Wings+of+History+Air+Museum&stick=H4sIAAAAAAAAAONgFmJQ4tZP1zesKCjPNki20FLMTrbSz8lPTizJzM-DM6wSS0qKEpNBzGIA8fiYPTQAAAA#fpstate=trskp&trifp=kpq%253DWings%252Bof%252BHistory%252BAir%252BMuseum%2526skpm%253D/g/1xpwk0c8%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipMpRFatnbN33b31Sdps0NXNky7e6RLPUMSODwCv=w179-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "7 min"
        },
        {
            "type": "Public transport",
            "duration": "47 min"
        }
        ],
        "rating": 4.6,
        "reviews": 107,
        "gps_coordinates": {
        "latitude": 37.0783357,
        "longitude": -121.6013437
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Coyote Lake",
        "link": "https://www.google.com/search?q=Coyote+Lake&stick=H4sIAAAAAAAAAONgFmJQ4tLP1Tcwzis3y0nXUsxOttLPyU9OLMnMz4MzrBJLSooSk0HMYgDfxW2CMwAAAA#fpstate=trskp&trifp=kpq%253DCoyote%252BLake%2526skpm%253D/m/03nw6lg%2526t%253Dd",
        "thumbnail": "http://t3.gstatic.com/images?q=tbn:ANd9GcR4rbntj-cIwcnux9XqS0ZUvZ9_phabaVcjyhgBQHsI2_Pg6FXAt7pXMg",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "27 min"
        }
        ],
        "rating": 4.4,
        "reviews": 69,
        "gps_coordinates": {
        "latitude": 37.0928891,
        "longitude": -121.53842759999999
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Anderson Lake County Park",
        "link": "https://www.google.com/search?q=Anderson+Lake+County+Park&stick=H4sIAAAAAAAAAONgFmJQ4tLP1TcwTTKMr0jXUsxOttLPyU9OLMnMz4MzrBJLSooSk0HMYgCPxyq6MwAAAA#fpstate=trskp&trifp=kpq%253DAnderson%252BLake%252BCounty%252BPark%2526skpm%253D/m/05b1_xg%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipP-zN1dK0Ob3k8boyxaT8RvJctt-A31LWPQ_RUq=w160-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "7 min"
        },
        {
            "type": "Public transport",
            "duration": "57 min"
        }
        ],
        "rating": 4.4,
        "reviews": 950,
        "gps_coordinates": {
        "latitude": 37.1633481,
        "longitude": -121.6302303
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Cinnabar Hills Golf Club",
        "link": "https://www.google.com/search?q=Cinnabar+Hills+Golf+Club&stick=H4sIAAAAAAAAAONgFmJQ4tLP1TcwKStPyUvTUsxOttLPyU9OLMnMz4MzrBJLSooSk0HMYgAPi2d4MwAAAA#fpstate=trskp&trifp=kpq%253DCinnabar%252BHills%252BGolf%252BClub%2526skpm%253D/m/04vwdnf%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipMeQwgCysyVRmdNBMS_tYkKKBVQtLm1wGZorDP0=w179-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "16 min"
        }
        ],
        "rating": 4.6,
        "reviews": 949,
        "gps_coordinates": {
        "latitude": 37.1695778,
        "longitude": -121.75079439999999
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Santa Teresa County Park",
        "link": "https://www.google.com/search?q=Santa+Teresa+County+Park&stick=H4sIAAAAAAAAAONgFmJQ4tLP1TcwKk8yLSzTUsxOttLPyU9OLMnMz4MzrBJLSooSk0HMYgCvzyC-MwAAAA#fpstate=trskp&trifp=kpq%253DSanta%252BTeresa%252BCounty%252BPark%2526skpm%253D/m/02wb5qv%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipM1rrbudKTsEDNI0A8kpegh6TfeVrJKPy6zyNGx=w160-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "17 min"
        },
        {
            "type": "Public transport",
            "duration": "1 h 7 min"
        }
        ],
        "rating": 4.6,
        "reviews": 1586,
        "gps_coordinates": {
        "latitude": 37.213916999999995,
        "longitude": -121.7876354
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Calero County Park",
        "link": "https://www.google.com/search?q=Calero+County+Park&stick=H4sIAAAAAAAAAONgFmJQ4tZP1zcsSTPPLS4v0lLMTrbSz8lPTizJzM-DM6wSS0qKEpNBzGIASO5UyzQAAAA#fpstate=trskp&trifp=kpq%253DCalero%252BCounty%252BPark%2526skpm%253D/g/1tf7mswr%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipNJib7bmS1jd00B4t3ZpT3vl6vnsnLO9MTH7iER=w213-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "17 min"
        }
        ],
        "rating": 4.6,
        "reviews": 650,
        "gps_coordinates": {
        "latitude": 37.1758664,
        "longitude": -121.7619524
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Mt. Madonna County Park",
        "link": "https://www.google.com/search?q=Mt.+Madonna+County+Park&stick=H4sIAAAAAAAAAONgFmJQ4tZP1zcsSS80Ky8w1VLMTrbSz8lPTizJzM-DM6wSS0qKEpNBzGIAsna9JDQAAAA#fpstate=trskp&trifp=kpq%253DMt.%252BMadonna%252BCounty%252BPark%2526skpm%253D/g/1tgq6wp5%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipOcIvY05rW20T91NlMFBxt8rHx-H4HWBDDgd3Ct=w160-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "26 min"
        }
        ],
        "rating": 4.8,
        "reviews": 669,
        "gps_coordinates": {
        "latitude": 37.0029592,
        "longitude": -121.7085492
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Coyote Valley Open Space Preserve",
        "link": "https://www.google.com/search?q=Coyote+Valley+Open+Space+Preserve&stick=H4sIAAAAAAAAAONgFmJQ4tVP1zc0TEqqyMutsKjQUsxOttLPyU9OLMnMz4MzrBJLSooSk0HMYgAJIlW_NgAAAA#fpstate=trskp&trifp=kpq%253DCoyote%252BValley%252BOpen%252BSpace%252BPreserve%2526skpm%253D/g/11bbxnmx8x%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipOGgQepAuKCMN6PW1LjKJe9vdOjTOgsB6hkiNfh=w160-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "15 min"
        },
        {
            "type": "Public transport",
            "duration": "59 min"
        }
        ],
        "rating": 4.8,
        "reviews": 302,
        "gps_coordinates": {
        "latitude": 37.167252,
        "longitude": -121.73495199999999
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Fortino Winery and Event Center",
        "link": "https://www.google.com/search?q=Fortino+Winery+and+Event+Center&stick=H4sIAAAAAAAAAONgFmJQ4tZP1zcsSc_KyTLP01LMTrbSz8lPTizJzM-DM6wSS0qKEpNBzGIA6i6PjDQAAAA#fpstate=trskp&trifp=kpq%253DFortino%252BWinery%252Band%252BEvent%252BCenter%2526skpm%253D/g/1tgjlj7n%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipOjYDZ_PY52_CHdpqsorje4hEOW46AguxQvTgfS=w160-h240-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "16 min"
        }
        ],
        "rating": 4.7,
        "reviews": 105,
        "gps_coordinates": {
        "latitude": 37.0139212,
        "longitude": -121.6531887
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Kirigin Cellars",
        "link": "https://www.google.com/search?q=Kirigin+Cellars&stick=H4sIAAAAAAAAAONgFmJQ4tZP1zcsKyzJMDBJ11LMTrbSz8lPTizJzM-DM6wSS0qKEpNBzGIATNJ8rjQAAAA#fpstate=trskp&trifp=kpq%253DKirigin%252BCellars%2526skpm%253D/g/1vqth04g%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipO1-acpLjAFN7QHP4eolktRAJyUHGZb-yu1Cg_2=w180-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "12 min"
        }
        ],
        "rating": 4.6,
        "reviews": 212,
        "gps_coordinates": {
        "latitude": 37.0467288,
        "longitude": -121.65269029999999
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Mount Madonna",
        "link": "https://www.google.com/search?q=Mount+Madonna&stick=H4sIAAAAAAAAAONgFmJQ4tVP1zc0TDfIzTDIzanQUsxOttLPyU9OLMnMz4MzrBJLSooSk0HMYgBBp7h_NgAAAA#fpstate=trskp&trifp=kpq%253DMount%252BMadonna%2526skpm%253D/g/11g0mh0mlx%2526t%253Dd",
        "thumbnail": "http://t1.gstatic.com/images?q=tbn:ANd9GcQreKOsVcn8Y2-a22eJfOAWBRuWiVwsed9vGTieKMgWRwEFBaU0zqYuFw",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "29 min"
        }
        ],
        "rating": 4.8,
        "reviews": 49,
        "gps_coordinates": {
        "latitude": 37.0121721,
        "longitude": -121.7049481
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Sarah's Vineyard",
        "link": "https://www.google.com/search?q=Sarah's+Vineyard&stick=H4sIAAAAAAAAAONgFmJQ4tZP1zcsSUmrrDJM0VLMTrbSz8lPTizJzM-DM6wSS0qKEpNBzGIA2kQe2TQAAAA#fpstate=trskp&trifp=kpq%253DSarah's%252BVineyard%2526skpm%253D/g/1tdfyz1d%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipMUYYP5_oW7pOP-CAbW7aDyTuJ8hoxMSXe4C_Zj=w160-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "16 min"
        }
        ],
        "rating": 4.6,
        "reviews": 102,
        "gps_coordinates": {
        "latitude": 37.0137116,
        "longitude": -121.64467429999999
    }
    },
    {
        "category": "Ponto de interesse",
        "name": "Lion Ranch Vineyards & Winery",
        "link": "https://www.google.com/search?q=Lion+Ranch+Vineyards+%26+Winery&stick=H4sIAAAAAAAAAONgFmJQ4tVP1zc0TM4uLDYyMYjXUsxOttLPyU9OLMnMz4MzrBJLSooSk0HMYgAAx898NgAAAA#fpstate=trskp&trifp=kpq%253DLion%252BRanch%252BVineyards%252B%252526%252BWinery%2526skpm%253D/g/11ckqs240_%2526t%253Dd",
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipO6rp69d6h3MTKM3pLcs61X-g9PEDM8QSGb5hGZ=w213-h120-k-no",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "8 min"
        },
        {
            "type": "Public transport",
            "duration": "57 min"
        }
        ],
        "rating": 4.8,
        "reviews": 26,
        "gps_coordinates": {
        "latitude": 37.081192,
        "longitude": -121.6219031
    }
    },
    {
        "category": "Estação de trem",
        "name": "Morgan Hill",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "6 min"
        },
        {
            "type": "Walking",
            "duration": "40 min"
        }
        ],
        "gps_coordinates": {
        "latitude": 37.129725,
        "longitude": -121.65055989999999
    }
    },
    {
        "category": "Estação de trem",
        "name": "San Martin",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "7 min"
        },
        {
            "type": "Walking",
            "duration": "1 h 28 min"
        }
        ],
        "gps_coordinates": {
        "latitude": 37.085740699999995,
        "longitude": -121.6104273
    }
    },
    {
        "category": "Aeroporto",
        "name": "Aeroporto Internacional de São Francisco",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "57 min"
        },
        {
            "type": "Public transport",
            "duration": "3 h 45 min"
        }
        ],
        "gps_coordinates": {
        "latitude": 37.6188889,
        "longitude": -122.37556
    }
    },
    {
        "category": "Aeroporto",
        "name": "Aeroporto Internacional de San José",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "29 min"
        },
        {
            "type": "Public transport",
            "duration": "1 h 52 min"
        }
        ],
        "gps_coordinates": {
        "latitude": 37.362777799999996,
        "longitude": -121.92917
    }
    },
    {
        "category": "Aeroporto",
        "name": "Aeroporto Internacional de Oakland",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "58 min"
        },
        {
            "type": "Public transport",
            "duration": "3 h 12 min"
        }
        ],
        "gps_coordinates": {
        "latitude": 37.7213889,
        "longitude": -122.22111
    }
    },
    {
        "category": "Restaurante mexicano",
        "name": "La Hacienda Restaurant",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipNNZxSaBUuWclL8nc2lMXj6WZuXI8fr1JJWaPdm=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "2 min"
        },
        {
            "type": "Walking",
            "duration": "8 min"
        },
        {
            "type": "Public transport",
            "duration": "59 min"
        }
        ],
        "rating": 4.2,
        "reviews": 595,
        "gps_coordinates": {
        "latitude": 37.130297299999995,
        "longitude": -121.6311685
    }
    },
    {
        "category": "Restaurante refinado",
        "name": "Ladera Grill",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipNwsK_DvBQ8A8E5sY8mJS1xfA2IlQDKDlZ7p4oV=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "8 min"
        },
        {
            "type": "Public transport",
            "duration": "32 min"
        },
        {
            "type": "Walking",
            "duration": "44 min"
        }
        ],
        "rating": 4.4,
        "reviews": 1102,
        "gps_coordinates": {
        "latitude": 37.1281457,
        "longitude": -121.65282479999999
    }
    },
    {
        "category": "Restaurante norte-americano",
        "name": "Betsy's Restaurant",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipOqaHLWBV13KlyS6ieaiYHwU1z29jD7RQ5ugVYa=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "5 min"
        },
        {
            "type": "Public transport",
            "duration": "37 min"
        },
        {
            "type": "Walking",
            "duration": "39 min"
        }
        ],
        "rating": 4.7,
        "reviews": 621,
        "gps_coordinates": {
        "latitude": 37.1130121,
        "longitude": -121.63803159999999
    }
    },
    {
        "category": "Restaurante",
        "name": "Burritos Tacos y Mas",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipOS70b8lFTNHBQrKW_mgwVycPCfFOxUQzs2MFVN=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "4 min"
        },
        {
            "type": "Walking",
            "duration": "13 min"
        },
        {
            "type": "Public transport",
            "duration": "1 h 6 min"
        }
        ],
        "rating": 4.6,
        "reviews": 141,
        "gps_coordinates": {
        "latitude": 37.133522899999996,
        "longitude": -121.6296349
    }
    },
    {
        "category": "Restaurante",
        "name": "MOHI Farm",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipNVApB7XzRJR0wXNIWzF0HgkIh2iJaxriBN8cCP=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "7 min"
        },
        {
            "type": "Public transport",
            "duration": "34 min"
        },
        {
            "type": "Walking",
            "duration": "41 min"
        }
        ],
        "rating": 4.3,
        "reviews": 106,
        "gps_coordinates": {
        "latitude": 37.1286081,
        "longitude": -121.6511197
    }
    },
    {
        "category": "Restaurante",
        "name": "Maurizio's Restaurtant",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipOhvftOjNDEKp1blkr2gu2NtfS5WNym3XDnW7mN=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "8 min"
        },
        {
            "type": "Public transport",
            "duration": "34 min"
        },
        {
            "type": "Walking",
            "duration": "44 min"
        }
        ],
        "rating": 4.6,
        "reviews": 487,
        "gps_coordinates": {
        "latitude": 37.1299718,
        "longitude": -121.653431
    }
    },
    {
        "category": "Restaurante de café da manhã",
        "name": "Yolked Extreme Breakfast",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipNYMknC5neoRJxe5sG2YcmC-WbAseHCDvvcSIng=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "3 min"
        },
        {
            "type": "Walking",
            "duration": "20 min"
        },
        {
            "type": "Public transport",
            "duration": "52 min"
        }
        ],
        "rating": 4.5,
        "reviews": 928,
        "gps_coordinates": {
        "latitude": 37.1303439,
        "longitude": -121.6376221
    }
    },
    {
        "category": "Restaurante de café da manhã",
        "name": "PAPA'S PLACE",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipPDgFvj83kGnEOri4JomoryoiK-ir3Qlx993LXO=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "7 min"
        },
        {
            "type": "Public transport",
            "duration": "34 min"
        },
        {
            "type": "Walking",
            "duration": "45 min"
        }
        ],
        "rating": 4.8,
        "reviews": 150,
        "gps_coordinates": {
        "latitude": 37.1287944,
        "longitude": -121.653382
    }
    },
    {
        "category": "Restaurante norte-americano",
        "name": "Noah's Bar & Bistro",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipOanuB_wlR3BtkMB8FYbn1mcg1QCbTcsMJ3huRM=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "7 min"
        },
        {
            "type": "Public transport",
            "duration": "34 min"
        },
        {
            "type": "Walking",
            "duration": "45 min"
        }
        ],
        "rating": 4.5,
        "reviews": 449,
        "gps_coordinates": {
        "latitude": 37.1298546,
        "longitude": -121.65364989999999
    }
    },
    {
        "category": "Restaurante italiano",
        "name": "Mama Mia's Mediterranean & Italian Cuisine - Morgan Hill",
        "thumbnail": "https://lh3.googleusercontent.com/p/AF1QipM1uKwkIGD95ar5r240O0oGPBbeRq6zc0wPpWqr=k",
        "transportations": [
        {
            "type": "Taxi",
            "duration": "5 min"
        },
        {
            "type": "Walking",
            "duration": "33 min"
        },
        {
            "type": "Public transport",
            "duration": "38 min"
        }
        ],
        "rating": 4.3,
        "reviews": 629,
        "gps_coordinates": {
        "latitude": 37.1273674,
        "longitude": -121.64624859999999
    }
    }
    ],
    "hotel_class": "Hotel 2 estrelas",
    "extracted_hotel_class": 2,
    "images": [
    {
        "thumbnail": "https://lh3.googleusercontent.com/gps-proxy/ALd4DhGUWVzesvbocv3NjayFqkGqpluLhv1rIaFbWujvxgdjiPIpGDZyHG4k3VuxU6kNN_Nf75-i9VY-rlN0wBrTU1U8FdA8tSKaKNdL9TioSHevB1eCwepsXACq1zV8J4RGjzzn1l__hvlwfhMNL-ewcFvIE6Xtm_p3uk1WLPSlLep2IPsawuZXDppf=s287-w287-h192-n-k-no-v1",
        "original_image": "https://d2hyz2bfif3cr8.cloudfront.net/imageRepo/3/0/75/619/592/CA991POOL2_S.jpg"
    },
    {
        "thumbnail": "https://lh3.googleusercontent.com/gps-proxy/ALd4DhHtgET3f26oM1TLrFvQXNztiydNso_g0rHwEg1MUy6v4CnLKSOASAvTqAT4tPxgkisHlhsy2uhqharXOGUgh60ID09D8n4VeYWtelw1PFYOI5eoDe8t4uzkC9_jvabfWmYI0JHjzjbf2CFFTXHsyCeXEXA6ANf5UpifWRyQUOY5qL713gk8bqNK=s287-w287-h192-n-k-no-v1",
        "original_image": "https://d2hyz2bfif3cr8.cloudfront.net/imageRepo/3/0/75/619/502/CA991NQQ02_S.jpg"
    },
    {
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipOqXONc_B3zLPlwnZ3yaubNeSGI0YrodZb5Yo9T=s287-w287-h192-n-k-no-v1",
        "original_image": "https://lh5.googleusercontent.com/p/AF1QipOqXONc_B3zLPlwnZ3yaubNeSGI0YrodZb5Yo9T=s10000"
    },
    {
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipM9HSUpow-fonsAhxL6RBlaGJoja4-2ROVr5gWO=s287-w287-h192-n-k-no-v1",
        "original_image": "https://lh5.googleusercontent.com/p/AF1QipM9HSUpow-fonsAhxL6RBlaGJoja4-2ROVr5gWO=s10000"
    },
    {
        "thumbnail": "https://lh3.googleusercontent.com/gps-proxy/ALd4DhF8j92-mPB5fbedb6oOmXgJPhP-F_Nm25BJh1wFedcY_F4W83N2y9MkCD9cGtBz-L1CKLUZgqTBGCJo4aIELOcrB0JWboYe7qB1vzLgte7RiKMNkCltTuohcFMzG22achI682ySIVqxNlRJ31mj34MlsP-_wm4gBrdJAHERSJjFJYeSOIgj8Goa=s287-w287-h192-n-k-no-v1",
        "original_image": "https://d2hyz2bfif3cr8.cloudfront.net/imageRepo/3/0/75/619/236/CA991Breakfast06_S.jpg"
    },
    {
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipP-jfiXqwGuKMMkpRC4gbIc8u0uLORyiyFAQ6Ih=s287-w287-h192-n-k-no-v1",
        "original_image": "https://lh5.googleusercontent.com/p/AF1QipP-jfiXqwGuKMMkpRC4gbIc8u0uLORyiyFAQ6Ih=s10000"
    },
    {
        "thumbnail": "https://lh5.googleusercontent.com/p/AF1QipPzeYWfe_6_cKnArw99pu8otPz6UYBB_kygQg_0=s287-w287-h192-n-k-no-v1",
        "original_image": "https://lh5.googleusercontent.com/p/AF1QipPzeYWfe_6_cKnArw99pu8otPz6UYBB_kygQg_0=s10000"
    },
    {
        "thumbnail": "https://lh3.googleusercontent.com/gps-proxy/ALd4DhGIpJC8WcbtrajEsqAggZ5AruW8xXRj-afDs3HU7jSDqvUXyZZoNSL5PJc0QCl5v50chCg60C5wFAVtngp-_RBEFKHKdlYuONi_Xpwb-dBjJXnctNTAhOh_9rTXGFufL6bSomI-L-y_w16DOzoYMWMDqifUi89_NfAYFUL3BPMvQDJ02NzKCTuj=s287-w287-h192-n-k-no-v1",
        "original_image": "https://d2hyz2bfif3cr8.cloudfront.net/imageRepo/3/0/75/619/267/CA991Lobby02_S.jpg"
    },
    {
        "thumbnail": "https://lh3.googleusercontent.com/gps-proxy/ALd4DhEcqZr-8TRCTCIBD9xu33aJ5-3sPRp0nrnogDtcb-iSvc-WQkSfv_ruxId67Pg53_2RAHbCSkgbc6ZZeAndIgQisVWVjZ21yddhTpMCIA7I970QyXCaTZM-pIqGAYDMuKoHoisdIdRdp6YuSqJYxRrDPLtoC0QXE36Sil4fLH2duPOYZA1jIJa2Ww=s287-w287-h192-n-k-no-v1",
        "original_image": "https://d2hyz2bfif3cr8.cloudfront.net/imageRepo/3/0/75/619/526/CA991NQQ04_S.jpg"
    }
    ],
    "overall_rating": 2.3,
    "reviews": 343,
    "ratings": [
    {
        "stars": 5,
        "count": 55
    },
    {
        "stars": 4,
        "count": 29
    },
    {
        "stars": 3,
        "count": 42
    },
    {
        "stars": 2,
        "count": 41
    },
    {
        "stars": 1,
        "count": 176
    }
    ],
    "location_rating": 3.2,
    "reviews_breakdown": [
    {
        "name": "Transporte Público",
        "description": "Public transit",
        "total_mentioned": 5,
        "positive": 2,
        "negative": 2,
        "neutral": 1
    },
    {
        "name": "Ginástica",
        "description": "Fitness",
        "total_mentioned": 25,
        "positive": 7,
        "negative": 14,
        "neutral": 4
    },
    {
        "name": "Vida Noturna",
        "description": "Nightlife",
        "total_mentioned": 5,
        "positive": 0,
        "negative": 4,
        "neutral": 1
    },
    {
        "name": "Café Da Manhã",
        "description": "Breakfast",
        "total_mentioned": 25,
        "positive": 7,
        "negative": 15,
        "neutral": 3
    },
    {
        "name": "Familiar",
        "description": "Family friendly",
        "total_mentioned": 13,
        "positive": 3,
        "negative": 10,
        "neutral": 0
    },
    {
        "name": "Hidromassagem",
        "description": "Hot tub",
        "total_mentioned": 11,
        "positive": 3,
        "negative": 7,
        "neutral": 1
    },
    {
        "name": "Segurança",
        "description": "Safety",
        "total_mentioned": 13,
        "positive": 2,
        "negative": 11,
        "neutral": 0
    },
    {
        "name": "Wi-Fi",
        "description": "Wi-Fi",
        "total_mentioned": 11,
        "positive": 4,
        "negative": 7,
        "neutral": 0
    },
    {
        "name": "TV",
        "description": "Room entertainment",
        "total_mentioned": 15,
        "positive": 0,
        "negative": 14,
        "neutral": 1
    },
    {
        "name": "Acessibilidade",
        "description": "Accessibility",
        "total_mentioned": 9,
        "positive": 1,
        "negative": 8,
        "neutral": 0
    },
    {
        "name": "Quarto",
        "description": "Room amenities",
        "total_mentioned": 33,
        "positive": 7,
        "negative": 24,
        "neutral": 2
    },
    {
        "name": "Ar-Condicionado",
        "description": "Air conditioning",
        "total_mentioned": 7,
        "positive": 1,
        "negative": 5,
        "neutral": 1
    },
    {
        "name": "Piscina",
        "description": "Pool",
        "total_mentioned": 10,
        "positive": 4,
        "negative": 5,
        "neutral": 1
    },
    {
        "name": "Cozinha",
        "description": "Kitchen",
        "total_mentioned": 21,
        "positive": 3,
        "negative": 18,
        "neutral": 0
    },
    {
        "name": "Restaurante",
        "description": "Restaurant",
        "total_mentioned": 12,
        "positive": 3,
        "negative": 5,
        "neutral": 4
    },
    {
        "name": "Estacionamento",
        "description": "Parking",
        "total_mentioned": 12,
        "positive": 2,
        "negative": 9,
        "neutral": 1
    },
    {
        "name": "Jantar",
        "description": "Food and Beverage",
        "total_mentioned": 8,
        "positive": 1,
        "negative": 6,
        "neutral": 1
    },
    {
        "name": "Bar",
        "description": "Bar or lounge",
        "total_mentioned": 5,
        "positive": 1,
        "negative": 4,
        "neutral": 0
    },
    {
        "name": "Local",
        "description": "Location",
        "total_mentioned": 12,
        "positive": 7,
        "negative": 3,
        "neutral": 2
    },
    {
        "name": "Ambiente",
        "description": "Atmosphere",
        "total_mentioned": 6,
        "positive": 4,
        "negative": 2,
        "neutral": 0
    },
    {
        "name": "Limpeza",
        "description": "Cleanliness",
        "total_mentioned": 113,
        "positive": 18,
        "negative": 90,
        "neutral": 5
    },
    {
        "name": "Propriedade",
        "description": "Property",
        "total_mentioned": 110,
        "positive": 20,
        "negative": 86,
        "neutral": 4
    },
    {
        "name": "Serviço",
        "description": "Service",
        "total_mentioned": 81,
        "positive": 16,
        "negative": 63,
        "neutral": 2
    },
    {
        "name": "Local Para Dormir",
        "description": "Sleep",
        "total_mentioned": 68,
        "positive": 10,
        "negative": 58,
        "neutral": 0
    },
    {
        "name": "Banheiro",
        "description": "Bathroom and toiletries",
        "total_mentioned": 66,
        "positive": 5,
        "negative": 58,
        "neutral": 3
    }
    ]
}*/