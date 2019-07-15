import os

from pypinyin import pinyin, lazy_pinyin, Style

if __name__ == '__main__':
    # with open(os.path.join(os.path.dirname(__file__),
    #                        "../assets/pinyin_simp.dict.yaml"), "r", encoding='utf8') as d:
    #     max: int = 0
    #     for line in d.readlines():
    #         units = line.split('\t')
    #         if len(units) == 3 and len(units[0]) > 1:
    #             units[2] = units[2][:-1]
    #             if int(units[2]) > max:
    #                 max = int(units[2])

    print(pinyin("藏", heteronym=True))
