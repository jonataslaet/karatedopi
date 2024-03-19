import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'beltEnumTranslation',
})
export class BeltEnumTranslationPipe implements PipeTransform {
    transform(value: string): string {
        switch (value) {
        case 'WHITE':
            return 'Faixa Branca';
        case 'YELLOW':
            return 'Faixa Amarela';
        case 'RED':
            return 'Faixa Vermelha';
        case 'ORANGE':
            return 'Faixa Laranja';
        case 'GREEN':
            return 'Faixa Verde';
        case 'PURPLE':
            return 'Faixa Roxa';
        case 'BROWN':
            return 'Faixa Marrom';
        case 'BLACK_01':
            return 'Shodan - 1º Dan';
        case 'BLACK_02':
            return 'Nidan - 2º Dan';
        case 'BLACK_03':
            return 'Sandan - 3º Dan';
        case 'BLACK_04':
            return 'Yondan - 4º Dan';
        case 'BLACK_05':
            return 'Godan - 5º Dan';
        case 'BLACK_06':
            return 'Rokudan - 6º Dan';
        case 'BLACK_07':
            return 'Shichidan - 7º Dan';
        case 'BLACK_08':
            return 'Hachidan - 8º Dan';
        case 'BLACK_09':
            return 'Kudan - 9º Dan';
        case 'BLACK_10':
            return 'Judan - 10º Dan';
        default:
            return value;
        }
    }
}
