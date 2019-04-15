/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.geronimo.daytrader.javaee6.entities;

import java.lang.reflect.Method;
import org.apache.geronimo.daytrader.javaee6.entities.AccountProfileDataBean;
import org.apache.geronimo.daytrader.javaee6.utils.TradeConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.isA;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
public class AccountProfileDataBeanTest {

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testGetRandomInstance() throws Exception {
    PowerMockito.mockStatic(TradeConfig.class);

    PowerMockito.doReturn("foo").when(TradeConfig.class);
    TradeConfig.rndUserID();

    PowerMockito.doReturn("bar").when(TradeConfig.class);
    TradeConfig.rndFullName();

    PowerMockito.doReturn("baz").when(TradeConfig.class);
    TradeConfig.rndAddress();

    PowerMockito.doReturn("foo").when(TradeConfig.class, method(TradeConfig.class, "rndEmail", String.class)).withArguments(isA(String.class));

    PowerMockito.doReturn("bar").when(TradeConfig.class);
    TradeConfig.rndCreditCard();

    final AccountProfileDataBean accountProfileDataBean = AccountProfileDataBean.getRandomInstance();
    Assert.assertEquals("\n\tAccount Profile Data for userID:foo\n\t\t   passwd:foo\n\t\t   fullName:bar\n\t\t" + 
    "    address:baz\n\t\t      email:foo\n\t\t creditCard:bar", accountProfileDataBean.toString());
  }

  @Test
  public void testHashCodeEmpty() {
    final AccountProfileDataBean accountProfileDataBean = new AccountProfileDataBean();
    Assert.assertEquals(0,  accountProfileDataBean.hashCode());
  }

  @Test
  public void testHashCode() {
    final AccountProfileDataBean accountProfileDataBean = new AccountProfileDataBean("foo", "bar", "baz", "foo", "bar", "baz");
    Assert.assertEquals(101574, accountProfileDataBean.hashCode());
  }

  @Test
  public void testPrintToString() throws Exception {
    final AccountProfileDataBean accountProfileDataBean = new AccountProfileDataBean("foo", "bar", "baz", "foo", "bar", "baz");
    Assert.assertEquals("\n\tAccount Profile Data for userID:foo\n\t\t   passwd:bar\n\t\t   fullName:baz\n\t\t" + 
    "    address:foo\n\t\t      email:bar\n\t\t creditCard:baz", accountProfileDataBean.toString());
    accountProfileDataBean.print();
  }

  @Test
  public void testToHTML() {
    final AccountProfileDataBean accountProfileDataBean = new AccountProfileDataBean("foo", "bar", "baz", "foo", "bar", "baz");
    Assert.assertEquals("<BR>Account Profile Data for userID: <B>foo</B><LI>   passwd:bar</LI><LI>   fullName:baz</LI><LI>" + 
    "    address:foo</LI><LI>      email:bar</LI><LI> creditCard:baz</LI>", accountProfileDataBean.toHTML());
  }

  @Test
  public void testEquals() {
    final AccountProfileDataBean accountProfileDataBean = new AccountProfileDataBean("foo", "bar", "baz", "foo", "bar", "baz");
    Assert.assertFalse(accountProfileDataBean.equals(null));

    Assert.assertTrue(accountProfileDataBean.equals(new AccountProfileDataBean("foo", "bar", "baz", "foo", "bar", "baz")));
    Assert.assertFalse(accountProfileDataBean.equals(new AccountProfileDataBean("bar", "bar", "baz", "foo", "bar", "baz")));

    accountProfileDataBean.setUserID(null);
    Assert.assertFalse(accountProfileDataBean.equals(new AccountProfileDataBean("foo", "bar", "baz", "foo", "bar", "baz")));
  }
}
