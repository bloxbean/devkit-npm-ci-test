# Yaci DevKit GitHub Actions CI Test using NPM distribution

A sample project that demonstrates how to use [Yaci DevKit](https://github.com/bloxbean/yaci-devkit) in a GitHub Actions CI test.  
It uses Yaci DevKit's npm installation.

### GitHub Action Yaml file configuration

The following configuration starts Yaci DevKit in Yaci Store mode, providing a Blockfrost-compatible API.

**Note:** If you want to start Ogmios and Kupo, use the `--enable-kupomios` option instead of `--enable-yaci-store`.


```yaml
      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: '20.8.0'

      - name: Install Yaci DevKit
        run: npm install -g @bloxbean/yaci-devkit

      - name: Start Yaci DevKit in background
        run: nohup  yaci-devkit up --enable-yaci-store &

      - name: Wait for Yaci DevKit to start
        run: |
          for i in {1..30}; do
            if nc -z localhost 8080; then
              echo "Yaci DevKit is up!"
              exit 0
            fi
            echo "Waiting for Yaci DevKit to start..."
            sleep 5
          done
          echo "Yaci DevKit failed to start" >&2
          exit 1

```

### Test Code

In the [test](https://github.com/satran004/devkit-npm-ci-test/tree/main/src/test/java/com/bloxbean/example) package, you can find the `TestHelper` class, which provides various utility methods such as `resetDevNet` and `topUpFund`.  
These methods can be used to reset the network between tests or top up funds to any address.

For **non-Java** projects, you can implement similar methods to invoke Yaci DevKit's admin endpoints for various operations.  

The default URL for **Yaci DevKit's admin endpoint** is `http://localhost:10000/`

A full list of supported admin APIs can be found at:
[http://localhost:10000/swagger-ui/index.html](http://localhost:10000/swagger-ui/index.html)

**Default Blockfrost-compatible indexer URL:** [http://localhost:8080/api/v1/](http://localhost:8080/api/v1/)


