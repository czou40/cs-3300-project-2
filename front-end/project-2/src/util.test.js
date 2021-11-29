import { encodeItem } from './util';
window.alert = jest.fn();

test('empty name parameter returns null', () => {
    window.alert.mockClear(); //otherwise Jest annoys you about not implementing window.alert()
    expect(encodeItem("", 0.05, 2)).toBeNull();
});
