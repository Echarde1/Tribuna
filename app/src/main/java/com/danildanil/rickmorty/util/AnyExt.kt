package com.danildanil.rickmorty.util

val Any.TAG: String
    get() {
        return this::class.java.run {
            if (isAnonymousClass) {
                enclosingClass?.simpleName ?: simpleName
            } else {
                simpleName
            }
        }
    }
