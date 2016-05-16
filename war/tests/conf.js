exports.config = {
  seleniumAddress: 'http://localhost:4444/wd/hub',
  baseUrl: 'http://localhost:8888',
  specs: ['tests.js'],
  capabilities: {browserName: 'chrome'},
  onPrepare: function(){
    browser.driver.get('http://localhost:8888');
  }
}