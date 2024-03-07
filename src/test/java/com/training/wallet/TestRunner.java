package com.training.wallet;

import com.training.wallet.controller.AuthenticationControllerTest;
import com.training.wallet.controller.WalletControllerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({AuthenticationControllerTest.class, WalletControllerTest.class})
public class TestRunner {
}