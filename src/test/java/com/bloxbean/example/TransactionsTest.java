package com.bloxbean.example;

import com.bloxbean.cardano.client.account.Account;
import com.bloxbean.cardano.client.api.model.Amount;
import com.bloxbean.cardano.client.api.model.Result;
import com.bloxbean.cardano.client.cip.cip20.MessageMetadata;
import com.bloxbean.cardano.client.common.model.Networks;
import com.bloxbean.cardano.client.function.helper.SignerProviders;
import com.bloxbean.cardano.client.metadata.Metadata;
import com.bloxbean.cardano.client.metadata.MetadataBuilder;
import com.bloxbean.cardano.client.quicktx.QuickTxBuilder;
import com.bloxbean.cardano.client.quicktx.Tx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionsTest {
    private final static String MNEMONIC = "test test test test test test test test test test test test test test test test test test test test test test test sauce";
    private final static Account sender = new Account(Networks.testnet(), MNEMONIC);

    private String receiverAddress = "addr_test1qq8phk43ndg0zf2l4xc5vd7gu4f85swkm3dy7fjmfkf6q249ygmm3ascevccsq5l5ym6khc3je5plx9t5vsa06jvlzls8el07z";

    private TestHelper testHelper = new TestHelper();

    @BeforeEach
    void beforeEach() {
        //If want to reset before every test
//        testHelper.resetDevNet();
    }

    @Test
    void simplePayment() {
        Metadata metadata = MetadataBuilder.createMetadata();
        metadata.put(BigInteger.valueOf(100), "This is second metadata");
        metadata.putNegative(200, -900);

        QuickTxBuilder quickTxBuilder = new QuickTxBuilder(testHelper.getBackendService());
        Tx tx = new Tx()
                .payToAddress(receiverAddress, Amount.ada(1.5))
                .attachMetadata(MessageMetadata.create().add("This is a test message"))
                .from(sender.baseAddress());

        Result<String> result = quickTxBuilder.compose(tx)
                .withSigner(SignerProviders.signerFrom(sender))
                .complete();

        System.out.println(result);

        System.out.println(result);
        assertThat(result.isSuccessful());

        testHelper.waitForTransaction(result);
    }


}
