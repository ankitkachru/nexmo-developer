/*
 * Copyright (c) 2011-2017 Nexmo Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.nexmo.quickstart.voice;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.JWTAuthMethod;
import com.nexmo.client.voice.Call;
import com.nexmo.client.voice.CallEvent;

import java.nio.file.FileSystems;

import static com.nexmo.quickstart.Util.configureLogging;
import static com.nexmo.quickstart.Util.envVar;

public class StreamAudioToCall {
    public static void main(String[] args) throws Exception {
        configureLogging();

        final String NEXMO_APPLICATION_ID = envVar("APPLICATION_ID");
        final String NEXMO_PRIVATE_KEY = envVar("PRIVATE_KEY");

        AuthMethod auth = new JWTAuthMethod(NEXMO_APPLICATION_ID, FileSystems.getDefault().getPath(NEXMO_PRIVATE_KEY));
        NexmoClient nexmo = new NexmoClient(auth);

        final String NEXMO_NUMBER = envVar("NEXMO_NUMBER");
        final String TO_NUMBER = envVar("TO_NUMBER");
        CallEvent call = nexmo.getVoiceClient().createCall(new Call(
                TO_NUMBER,
                NEXMO_NUMBER,
                "https://gist.githubusercontent.com/ChrisGuzman/d6add5b23a8cf913dcdc5a8eabc223ef/raw/a1eb52e0ce2d3cef98bab14d27f3adcdff2af881/long_talk.json"
        ));

        Thread.sleep(20000);

        final String UUID = call.getUuid();
        final String URL = "https://nexmo-community.github.io/ncco-examples/assets/voice_api_audio_streaming.mp3";

        nexmo.getVoiceClient().startStream(UUID, URL, 0);
        Thread.sleep(5000);
        nexmo.getVoiceClient().stopStream(UUID);
    }
}
