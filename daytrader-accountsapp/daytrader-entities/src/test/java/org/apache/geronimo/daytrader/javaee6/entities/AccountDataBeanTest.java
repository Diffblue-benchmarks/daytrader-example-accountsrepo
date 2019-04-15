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
import java.math.BigDecimal;
import java.util.Date;
import org.apache.geronimo.daytrader.javaee6.entities.AccountDataBean;
import org.apache.geronimo.daytrader.javaee6.entities.AccountProfileDataBean;
import org.apache.geronimo.daytrader.javaee6.utils.TradeConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyFloat;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
public class AccountDataBeanTest {

  @PrepareForTest({AccountDataBean.class, TradeConfig.class})
  @Test
  public void testGetRandomInstance() throws Exception {
    PowerMockito.mockStatic(TradeConfig.class);

    final Date creationDate = new Date(1555332012015L);
    final Date lastLogin = new Date(1555332612015L);
    PowerMockito.whenNew(Date.class).withParameterTypes(long.class)
      .withArguments(anyLong()).thenReturn(creationDate);
    PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(lastLogin);

    ((PowerMockitoStubber)PowerMockito
      .doReturn(new BigDecimal(0.1f))
      .doReturn(new BigDecimal(0.2f)))
      .when(
        TradeConfig.class,
        method(TradeConfig.class, "rndBigDecimal", float.class)
        ).withArguments(anyFloat());

    ((PowerMockitoStubber)PowerMockito.doReturn(0).doReturn(1).doReturn(2))
      .when(
        TradeConfig.class,
        method(TradeConfig.class, "rndInt", int.class)
        ).withArguments(anyInt());

    PowerMockito.doReturn("foo").when(TradeConfig.class);
    TradeConfig.rndUserID();

    final AccountDataBean accountDataBean = AccountDataBean.getRandomInstance();
    Assert.assertEquals("\n\tAccount Data for account: 0"
                + "\n\t\t   loginCount:1"
                + "\n\t\t  logoutCount:2"
                + "\n\t\t    lastLogin:Mon Apr 15 13:50:12 BST 2019"
                + "\n\t\t creationDate:Mon Apr 15 13:40:12 BST 2019"
                + "\n\t\t      balance:0.100000001490116119384765625"
                + "\n\t\t  openBalance:0.20000000298023223876953125"
                + "\n\t\t    profileID:foo", accountDataBean.toString());
  }

  @Test
  public void testHashCodeEmpty() {
    final AccountDataBean accountDataBean = new AccountDataBean();
    Assert.assertEquals(0, accountDataBean.hashCode());
  }

  @Test
  public void testHashCode() {
    final AccountDataBean accountDataBean = new AccountDataBean
    (
      0,
      1,
      2,
      new Date(1555332612015L),
      new Date(1555332012015L),
      new BigDecimal(0.1f),
      new BigDecimal(0.2f),
      "foo"
    );
    Assert.assertEquals(101574, accountDataBean.hashCode());
  }

  @Test
  public void testPrintToString() throws Exception {
    final AccountDataBean accountDataBean = new AccountDataBean
    (
      0,
      1,
      2,
      new Date(1555332612015L),
      new Date(1555332012015L),
      new BigDecimal(0.1f),
      new BigDecimal(0.2f),
      "foo"
    );
    Assert.assertEquals("\n\tAccount Data for account: 0"
                + "\n\t\t   loginCount:1"
                + "\n\t\t  logoutCount:2"
                + "\n\t\t    lastLogin:Mon Apr 15 13:50:12 BST 2019"
                + "\n\t\t creationDate:Mon Apr 15 13:40:12 BST 2019"
                + "\n\t\t      balance:0.100000001490116119384765625"
                + "\n\t\t  openBalance:0.20000000298023223876953125"
                + "\n\t\t    profileID:foo", accountDataBean.toString());

    accountDataBean.print();
    // Method not expected to return, testing that no exception is thrown
  }

  @Test
  public void testProfile() {
    final AccountDataBean accountDataBean = new AccountDataBean
    (
      0,
      1,
      2,
      new Date(1555332612015L),
      new Date(1555332012015L),
      new BigDecimal(0.1f),
      new BigDecimal(0.2f),
      "foo"
    );
    
    accountDataBean.setProfile(new AccountProfileDataBean
      (
        "foo",
        "bar",
        "baz",
        "foo",
        "bar",
        "baz"
      )
    );

    Assert.assertEquals("\n\tAccount Profile Data for userID:foo"
                + "\n\t\t   passwd:bar"
                + "\n\t\t   fullName:baz"
                + "\n\t\t    address:foo"
                + "\n\t\t      email:bar"
                + "\n\t\t creditCard:baz", accountDataBean.getProfile().toString());
  }

  @Test
  public void testToHTML() {
    final AccountDataBean accountDataBean = new AccountDataBean
    (
      0,
      1,
      2,
      new Date(1555332612015L),
      new Date(1555332012015L),
      new BigDecimal(0.1f),
      new BigDecimal(0.2f),
      "foo"
    );
    Assert.assertEquals("<BR>Account Data for account: <B>0</B>"
                + "<LI>   loginCount:1</LI>"
                + "<LI>  logoutCount:2</LI>"
                + "<LI>    lastLogin:Mon Apr 15 13:50:12 BST 2019</LI>"
                + "<LI> creationDate:Mon Apr 15 13:40:12 BST 2019</LI>"
                + "<LI>      balance:0.100000001490116119384765625</LI>"
                + "<LI>  openBalance:0.20000000298023223876953125</LI>"
                + "<LI>    profileID:foo</LI>", accountDataBean.toHTML());
  }

  @Test
  public void testEquals() {
    final AccountDataBean accountDataBean = new AccountDataBean
    (
      0,
      1,
      2,
      new Date(1555332612015L),
      new Date(1555332012015L),
      new BigDecimal(0.1f),
      new BigDecimal(0.2f),
      "foo"
    );
    Assert.assertFalse(accountDataBean.equals(null));

    Assert.assertTrue(accountDataBean.equals(new AccountDataBean
        (
          0,
          1,
          2,
          new Date(1555332612015L),
          new Date(1555332012015L),
          new BigDecimal(0.1f),
          new BigDecimal(0.2f),
          "foo"
        )
      )
    );
    Assert.assertFalse(accountDataBean.equals(new AccountDataBean
        (
          0,
          1,
          2,
          new Date(1555332612015L),
          new Date(1555332012015L),
          new BigDecimal(0.1f),
          new BigDecimal(0.2f),
          "bar"
        )
      )
    );

    accountDataBean.setProfileID(null);
    Assert.assertFalse(accountDataBean.equals(new AccountDataBean
        (
          0,
          1,
          2,
          new Date(1555332612015L),
          new Date(1555332012015L),
          new BigDecimal(0.1f),
          new BigDecimal(0.2f),
          "foo"
        )
      )
    );
  }
}
