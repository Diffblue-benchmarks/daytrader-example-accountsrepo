package org.apache.geronimo.daytrader.javaee6.utils;

import static org.mockito.Matchers.anyLong;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

import org.apache.geronimo.daytrader.javaee6.utils.TradeConfig;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.expectation.PowerMockitoStubber;

import java.lang.Exception;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

@RunWith(PowerMockRunner.class)
public class TradeConfigTest {

  @PrepareForTest({InetAddress.class, TradeConfig.class, System.class})
  @Test
  public void testRndNewUserID() throws Exception, UnknownHostException {
    PowerMockito.mockStatic(InetAddress.class); 
    final InetAddress inetAddress = PowerMockito.mock(InetAddress.class);
    PowerMockito.doReturn("testHost").when(inetAddress, method(InetAddress.class, "getHostName")).withNoArguments();
    PowerMockito.doReturn(inetAddress).when(InetAddress.class);
    InetAddress.getLocalHost();

    PowerMockito.mockStatic(System.class);
    PowerMockito.when(System.currentTimeMillis()).thenReturn(100L);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals("ru:testHost1000", tradeConfig.rndNewUserID());
    Assert.assertEquals("ru:testHost1001", tradeConfig.rndNewUserID());
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testGetOrderFee() throws Exception, UnknownHostException {
    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals(new BigDecimal(24.95).setScale(2, BigDecimal.ROUND_HALF_UP), tradeConfig.getOrderFee("buy"));
    Assert.assertEquals(new BigDecimal(24.95).setScale(2, BigDecimal.ROUND_HALF_UP), tradeConfig.getOrderFee("sell"));
    Assert.assertEquals(new BigDecimal(0.0).setScale(1, BigDecimal.ROUND_HALF_UP), tradeConfig.getOrderFee("foo"));
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testRndAddress() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(1.23);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals("1230 Oak St.", tradeConfig.rndAddress());
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testRndCreditCard() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(1.23);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals("123-1230-1230-1230", tradeConfig.rndCreditCard());
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testRndFullName() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(1.23);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals("first:1230 last:6150", tradeConfig.rndFullName());
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testRndEmail() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(1.23);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals("foo@123.com", tradeConfig.rndEmail("foo"));
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testRndPrice() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(1.23);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals(247f, tradeConfig.rndPrice(), 0);
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void getRandomPriceChangeFactor() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(1.0).thenReturn(0.6);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals(new BigDecimal(1.20).setScale(2, BigDecimal.ROUND_HALF_UP), tradeConfig.getRandomPriceChangeFactor());

    PowerMockito.when(random.nextDouble()).thenReturn(2.0).thenReturn(0.0);
    Assert.assertEquals(new BigDecimal(0.60).setScale(2, BigDecimal.ROUND_HALF_UP), tradeConfig.getRandomPriceChangeFactor());

    PowerMockito.when(random.nextDouble()).thenReturn(5.0).thenReturn(0.0);
    Assert.assertEquals(new BigDecimal(1), tradeConfig.getRandomPriceChangeFactor());
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testRndQuantity() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(1.23);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals(247f, tradeConfig.rndQuantity(), 0);
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testRndSymbol() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(1.23);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals("s:490", tradeConfig.rndSymbol());
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testRndSymbols() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(0.10).thenReturn(1.23);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals("s:490,s:490", tradeConfig.rndSymbols());
  }

  @PrepareForTest(TradeConfig.class)
  @Test
  public void testrndUserID() throws Exception, UnknownHostException {
    final Random random = PowerMockito.mock(Random.class);
    PowerMockito.when(random.nextDouble()).thenReturn(0.10);
    PowerMockito.whenNew(Random.class).withParameterTypes(long.class).withArguments(anyLong()).thenReturn(random);

    final TradeConfig tradeConfig = new TradeConfig();
    Assert.assertEquals("uid:1", tradeConfig.rndUserID());
    Assert.assertEquals("uid:2", tradeConfig.rndUserID());
    
    tradeConfig.setMAX_USERS(1);
    Assert.assertEquals("uid:0", tradeConfig.rndUserID());
  }
}
