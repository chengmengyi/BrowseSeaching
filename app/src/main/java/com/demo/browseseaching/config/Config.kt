package com.demo.browseseaching.config

import com.demo.browseseaching.bean.ServerBean

object Config {
    const val email=""
    const val url=""

    val localServerList= arrayListOf(
        ServerBean(
            host = "100.223.52.0",
            port = 100,
            pwd = "123456",
            country = "Japan",
            city = "Tokyo",
            method = "chacha20-ietf-poly1305",
        ),
        ServerBean(
            host = "100.223.52.78",
            port = 100,
            pwd = "123456",
            country = "TEST2",
            city = "TEST2_CITY",
            method = "chacha20-ietf-poly1305",
        ),
    )

    const val BROWSE_AD_CONF="""{
                "flower_click":15,
    "flower_show":50,
    "flower_open": [
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/3419835294",
            "flower_type": "cha",
            "flower_sort": 1
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/3419835294A",
            "flower_type": "kai",
            "flower_sort": 2
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/3419835294AA",
            "flower_type": "kai",
            "flower_sort": 3
        }
    ],
    "flower_bookmark": [
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/3419835294",
            "flower_type": "cha",
            "flower_sort": 1
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/3419835294A",
            "flower_type": "kai",
            "flower_sort": 2
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/3419835294AA",
            "flower_type": "kai",
            "flower_sort": 3
        }
    ],
     "flower_later": [
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/3419835294",
            "flower_type": "cha",
            "flower_sort": 1
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/3419835294A",
            "flower_type": "kai",
            "flower_sort": 2
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/3419835294AA",
            "flower_type": "kai",
            "flower_sort": 3
        }
    ],
    "flower_recent": [
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110A",
            "flower_type": "yuan",
            "flower_sort": 2
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110",
            "flower_type": "yuan",
            "flower_sort": 1
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110AA",
            "flower_type": "yuan",
            "flower_sort": 3
        }
    ],
    
       "flower_history": [
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110A",
            "flower_type": "yuan",
            "flower_sort": 2
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110",
            "flower_type": "yuan",
            "flower_sort": 1
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110AA",
            "flower_type": "yuan",
            "flower_sort": 3
        }
    ],
    "flower_connect_home": [
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110A",
            "flower_type": "yuan",
            "flower_sort": 2
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110",
            "flower_type": "yuan",
            "flower_sort": 1
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110AA",
            "flower_type": "yuan",
            "flower_sort": 3
        }
    ],
    "flower_connect_result": [
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110A",
            "flower_type": "yuan",
            "flower_sort": 2
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110",
            "flower_type": "yuan",
            "flower_sort": 1
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/2247696110AA",
            "flower_type": "yuan",
            "flower_sort": 3
        }
    ],
    "flower_connect": [
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/8691691433A",
            "flower_type": "cha",
            "flower_sort": 2
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/1033173712",
            "flower_type": "cha",
            "flower_sort": 1
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/1033173712AA",
            "flower_type": "cha",
            "flower_sort": 3
        }
    ],
    "flower_back": [
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/1033173712A",
            "flower_type": "cha",
            "flower_sort": 2
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/8691691433",
            "flower_type": "cha",
            "flower_sort": 1
        },
        {
            "flower_source": "admob",
            "flower_id": "ca-app-pub-3940256099942544/1033173712AA",
            "flower_type": "cha",
            "flower_sort": 3
        }
    ]
}"""
}