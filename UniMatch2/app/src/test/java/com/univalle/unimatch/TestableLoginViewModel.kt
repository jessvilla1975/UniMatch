package com.univalle.unimatch

import com.google.firebase.auth.FirebaseAuth
import com.univalle.unimatch.presentation.viewmodel.LoginViewModel

class TestableLoginViewModel(
    fakeAuth: FirebaseAuth
) : LoginViewModel(fakeAuth)