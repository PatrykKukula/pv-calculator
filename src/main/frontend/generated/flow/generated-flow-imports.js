import { injectGlobalCss } from 'Frontend/generated/jar-resources/theme-util.js';

import { css, unsafeCSS, registerStyles } from '@vaadin/vaadin-themable-mixin';
import $cssFromFile_0 from 'Frontend/styles/common-styles.css?inline';
import $cssFromFile_1 from 'Frontend/styles/app-layout-styles.css?inline';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/vertical-layout/theme/lumo/vaadin-vertical-layout.js';
import '@vaadin/notification/theme/lumo/vaadin-notification.js';
import 'Frontend/generated/jar-resources/flow-component-renderer.js';
import '@vaadin/login/theme/lumo/vaadin-login-form.js';
import '@vaadin/button/theme/lumo/vaadin-button.js';
import '@vaadin/tooltip/theme/lumo/vaadin-tooltip.js';
import 'Frontend/generated/jar-resources/disableOnClickFunctions.js';
import '@vaadin/app-layout/theme/lumo/vaadin-app-layout.js';
import '@vaadin/horizontal-layout/theme/lumo/vaadin-horizontal-layout.js';
import '@vaadin/text-field/theme/lumo/vaadin-text-field.js';
import '@vaadin/integer-field/theme/lumo/vaadin-integer-field.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/icon/theme/lumo/vaadin-icon.js';
import '@vaadin/dialog/theme/lumo/vaadin-dialog.js';
import '@vaadin/combo-box/theme/lumo/vaadin-combo-box.js';
import 'Frontend/generated/jar-resources/comboBoxConnector.js';
import '@vaadin/multi-select-combo-box/theme/lumo/vaadin-multi-select-combo-box.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';
import 'Frontend/generated/jar-resources/ReactRouterOutletElement.tsx';

injectGlobalCss($cssFromFile_0.toString(), 'CSSImport end', document);

injectGlobalCss($cssFromFile_1.toString(), 'CSSImport end', document);

const loadOnDemand = (key) => {
  const pending = [];
  if (key === 'f9a65e5e06a3d4a6f064bcc03a8d8e352ae95acc813f9145e05d5282151e0cea') {
    pending.push(import('./chunks/chunk-3bc0b5296aab143ff993664a3aa9ee83d486149939672f40a44278a311738b87.js'));
  }
  if (key === '435229c4a0ce73f3383e3cf4c20caed053d0a6e721a3dbfe33343832fb137c02') {
    pending.push(import('./chunks/chunk-d9f92b94ad46deec9b28da7a71b0f11a93fee7afee31f2269354b93c70b2ddd2.js'));
  }
  if (key === '2e65f38d3447a66c1a2d6da5ddbd93f16679f86eb53a0f2e3c7fd5e92a01e3e3') {
    pending.push(import('./chunks/chunk-5e46f0702ffd486a0106600e195c243ff331b310726db9fe3302b995bca4daba.js'));
  }
  if (key === '11dbadcfdcdeda11e5c4c981fd59997833fbf2e7d87404a6337dde7d43e98ecf') {
    pending.push(import('./chunks/chunk-5e46f0702ffd486a0106600e195c243ff331b310726db9fe3302b995bca4daba.js'));
  }
  if (key === 'e3f7d84df13c6a4591cf401022f866f6742fd1fb796a4483258aeab736324035') {
    pending.push(import('./chunks/chunk-0fec9b6a5f8155c5ddc6a9eb4334fa7d97d5de716f9328537fff2af6167e070e.js'));
  }
  if (key === 'feabc62586df413146da207da3dcfa8dbfbe34b240890a32840a6e54f66d0df5') {
    pending.push(import('./chunks/chunk-77dd7b58d98289d84fc8d7c0ae6b7722a68158e40d94319e4c95e88f9042dc44.js'));
  }
  if (key === '92e1004e22ccb05fb662e0d26754ee8d4afc3aa313496faf08d91c0236c489d7') {
    pending.push(import('./chunks/chunk-d1697a5540d41f969bc46acac3ea28e354198cac8e693978835f328b11359ca6.js'));
  }
  if (key === '3f750b95e0228386c51778890b2e5f8ef92e75628afb9b6dae661f48b180b5d4') {
    pending.push(import('./chunks/chunk-01a8231a6d6f9cb87f3c949556985ec31878587681ec54b80cc9d5841d1f4c38.js'));
  }
  if (key === '052c6db5e0156788e5f7931577330a12f8ccb3143af4d7727b7499e7847065e2') {
    pending.push(import('./chunks/chunk-3bc0b5296aab143ff993664a3aa9ee83d486149939672f40a44278a311738b87.js'));
  }
  if (key === 'f85e7b301875fb1ca18959d72588f265c3e21c4b742f953b8fed9e91fd37e65c') {
    pending.push(import('./chunks/chunk-1c038bc2677755ac819aa74f86c6a27317d0da9158364d867640ee64eb19b896.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}