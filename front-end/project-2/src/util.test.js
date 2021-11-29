import { encodeItem } from './util';
window.alert = jest.fn();

//include `window.alert.mockClear();` at start of every test so Jest won't annoy you about not implementing window.alert()
test('Item Name not allowed to be Empty', () => {
    window.alert.mockClear();
    expect(encodeItem("", 0.05, 2)).toBeNull();
});
test('Special Characters not allowed in Item Name', () => {
    window.alert.mockClear();
    expect(encodeItem("!@#$%^&*()[]\\/", 0.05, 2)).toBeNull();
});
test('Space-only Item Name not allowed', () => {
    window.alert.mockClear();
    expect(encodeItem("  ", 0.05, 2)).toBeNull();
});
test('Price value cannot be NaN', () => {
    window.alert.mockClear();
    expect(encodeItem("test123", "abc", 2)).toBeNull();
});
test('Price value cannot be negative', () => {
    window.alert.mockClear();
    expect(encodeItem("test123", -1.23, 2)).toBeNull();
});
test('Price value cannot be zero', () => {
    window.alert.mockClear();
    expect(encodeItem("test123", 0, 2)).toBeNull();
});
test('Quantity value cannot be NaN', () => {
    window.alert.mockClear();
    expect(encodeItem("test123", 0.05, "xyz")).toBeNull();
});
test('Quantity value cannot be negative', () => {
    window.alert.mockClear();
    expect(encodeItem("test123", 0.05, -2)).toBeNull();
});
test('Quantity value cannot be zero', () => {
    window.alert.mockClear();
    expect(encodeItem("test123", 0.05, 0)).toBeNull();
});
test('Quantity value cannot be a float', () => {
    window.alert.mockClear();
    expect(encodeItem("test123", 0.05, 0.02)).toBeNull();
});
test('Successful bill item submit (integer price)', () => {
    window.alert.mockClear();
    expect(encodeItem("test123", 7, 2)).not.toBeNull();
});
test('Successful bill item submit (float price)', () => {
    window.alert.mockClear();
    expect(encodeItem("test123", 0.05, 2)).not.toBeNull();
});
/*
test('temp', () => {
    window.alert.mockClear();
    expect(encodeItem("a***E", 0.05, 2)).toBeNull();
}); */