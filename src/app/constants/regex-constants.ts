export const Expressions = {
    'invalidName': /^[aA-zZ\s]*$/,
    'onlyNumbers': /^[0-9]+$/,
    'maxCharAllowed': /^.{0,50}$/,
    'invalidPasword': /[A-z]/ && /(?=.*\W)/ && /\d/,
    'invalidEmail': /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
};
